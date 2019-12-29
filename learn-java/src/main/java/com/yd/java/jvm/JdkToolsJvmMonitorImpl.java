package com.yd.java.jvm;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.yd.java.jvm.common.LocalHost;
import com.yd.java.jvm.entity.JstackInfo;
import com.yd.java.jvm.entity.JstatInfo;
import com.yd.java.jvm.entity.ProcessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvmstat.monitor.*;
import sun.jvmstat.monitor.event.HostEvent;
import sun.jvmstat.monitor.event.HostListener;
import sun.jvmstat.monitor.event.VmStatusChangeEvent;
import sun.tools.attach.HotSpotVirtualMachine;
import sun.tools.jstat.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class JdkToolsJvmMonitorImpl implements IJvmMonitor {

    public final static String pidMode = "//%s?mode=r";
    private final static Logger logger = LoggerFactory.getLogger(JdkToolsJvmMonitorImpl.class);
    private final ConcurrentHashMap<String, ProcessInfo> processInfosMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, JstatMonitoredWapper> jstatMonitoredWapperMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, VirtualMachine> virtualMachineMap = new ConcurrentHashMap<String, VirtualMachine>();

    private final ThreadLocal<ByteArrayOutputStream> jstatByteArrayStream = new ThreadLocal<ByteArrayOutputStream>();
    private final AtomicBoolean isInit = new AtomicBoolean(false);
    private final String jdkVersion = System.getProperty("java.version");
    private final String localIp = new LocalHost().getIp();

    private IVmEventListener activeVmEventListener;

    private sun.tools.jps.Arguments jpsArguments;
    private Field[] jstatInfoFields;
    private MonitoredHost monitoredHost;
    private IProcessInfoFilter processInfoFilter;
    private IClassNameFilter classNameFilter;

    @Override
    public List<ProcessInfo> getAllProcessInfo() {

        if (processInfosMap.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProcessInfo> processInfos = new ArrayList<>(processInfosMap.values());
        return processInfos;
    }

    @Override
    public void pendingJstatInfo(List<ProcessInfo> processInfos) {

        try {
            for (ProcessInfo processInfo : processInfos) {
                JstatInfo jstatInfo = new JstatInfo();
                for (Field field : jstatInfoFields) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    field.setAccessible(true);
                    String name = field.getName().toLowerCase();
                    String key = getJstatArguKey(name, String.valueOf(processInfo.getPid()));
                    JstatMonitoredWapper wapper = jstatMonitoredWapperMap.get(key);
                    if (wapper == null) {
                        synchronized (JstatMonitoredWapper.class) {
                            wapper = jstatMonitoredWapperMap.get(key);
                            if (wapper == null) {
                                wapper = new JstatMonitoredWapper();
                                if (processInfo.getJdkVersion().contains("1.8") && name.equalsIgnoreCase("gcPermCapacity")) {
                                    name = "gcmetacapacity";
                                }

                                String[] jstatArgs = {"-" + name, String.valueOf(processInfo.getPid()), "1000", "1"};
                                Arguments jstatArgument = new Arguments(jstatArgs);
                                wapper.setArguments(jstatArgument);
                                wapper.setMonitoredHost(monitoredHost);

                                jstatMonitoredWapperMap.put(key, wapper);
                            }
                        }
                    }

                    String result = logSamples(wapper);
                    field.set(jstatInfo, result);
                }
                processInfo.setJstatInfo(jstatInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String logSamples(final JstatMonitoredWapper jstatMonitoredWapper) throws MonitorException, IOException {
        final Arguments arguments = jstatMonitoredWapper.getArguments();

        //1、先初始化 virtual machine identifier
        VmIdentifier localVmIdentifier = jstatMonitoredWapper.getArguments().vmId();
        int interval = jstatMonitoredWapper.getArguments().sampleInterval();
        //2、then get monitored vm with monitored host
        MonitoredVm localMonitoredVm = jstatMonitoredWapper.getMonitoredVm();
        if (localMonitoredVm == null) {
            localMonitoredVm = monitoredHost.getMonitoredVm(localVmIdentifier, interval);// 这一步也比较耗
            jstatMonitoredWapper.setMonitoredVm(localMonitoredVm);
        }

        final JStatLogger localJStatLogger = new JStatLogger(localMonitoredVm);
        Object OptionOutputFormatter = null;
        if (arguments.isSpecialOption()) {
            OptionFormat optionFormat = arguments.optionFormat();
            OptionOutputFormatter = new OptionOutputFormatter(localMonitoredVm, (OptionFormat) optionFormat);
        }


        ByteArrayOutputStream baops = jstatByteArrayStream.get();
        if (baops == null) {
            baops = new ByteArrayOutputStream(1024);
            jstatByteArrayStream.set(baops);
        } else {
            baops.reset();
        }

        try (PrintStream printStream = new PrintStream(baops);) {
            localJStatLogger.logSamples((OutputFormatter) OptionOutputFormatter,
                    arguments.headerRate(), arguments.sampleInterval(),
                    arguments.sampleCount(), printStream);
            //这一步没什么耗时，主要操作就是cancel the simple task。但是真正定时采样输出的是在 logSamples 方法里面。所以目前调用 detach 方法 然并软。
            monitoredHost.detach(localMonitoredVm);
            localJStatLogger.stopLogging();//停止 采样输出
            return new String(baops.toByteArray());
        }
    }

    @Override
    public void jmapHisto(List<ProcessInfo> processInfos, boolean liveOrAll) {

        for (ProcessInfo processInfo : processInfos) {
            String pid = String.valueOf(processInfo.getPid());
            List<String> results = null;
            VirtualMachine localVirtualMachine = virtualMachineMap.get(pid);
            if (localVirtualMachine == null) {
                localVirtualMachine = attach(pid);
                logger.error("jmapHisto:pid[{}],localVirtualMachine[{}]", pid, localVirtualMachine);
                if (localVirtualMachine == null) {
                    results = Collections.emptyList();
                    processInfo.setJmapHisto(results);
                    continue;
                } else {
                    virtualMachineMap.put(pid, localVirtualMachine);
                }
            }

            try {
                InputStream localInputStream =
                        ((HotSpotVirtualMachine) localVirtualMachine).heapHisto(new Object[]{liveOrAll ? "-live" : "-all"});
                results = drain(localVirtualMachine, localInputStream, classNameFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            processInfo.setJmapHisto(results);
        }

    }

    private List<String> drain(VirtualMachine paramVirtualMachine, InputStream paramInputStream, IClassNameFilter classNameFilter) throws Exception {
        StringBuffer sbuffer = new StringBuffer();

        //1、fetch the result
        byte[] arrayOfByte = new byte['Ā'];
        int i;
        do {
            i = paramInputStream.read(arrayOfByte);
            if (i > 0) {
                String str = new String(arrayOfByte, 0, i, "UTF-8");
                sbuffer.append(str);
            }
        } while (i > 0);

        paramInputStream.close();

        //2、filter from the result
        List<String> result = new ArrayList<>();
        StringTokenizer stokenizer = new StringTokenizer(sbuffer.toString(), System.lineSeparator());
        while (stokenizer.hasMoreTokens()) {
            String line = stokenizer.nextToken();
            if (classNameFilter.accept(line)) {
                result.add(line);
            }
        }

        return result;
    }

    @Override
    public void jstackInfo(List<ProcessInfo> processInfos) {
        String[] args = {"-l"};
        for (ProcessInfo processInfo : processInfos) {
            String pid = String.valueOf(processInfo.getPid());
            try {
                processInfo.setJstackInfo(new JstackInfo(runThreadDump(pid, args)));
            } catch (Exception e) {
            }
        }
    }

    private String runThreadDump(String pid, String[] paramArrayOfString) throws Exception {
        VirtualMachine localVirtualMachine = null;
        try {
            localVirtualMachine = virtualMachineMap.get(pid);
            if (localVirtualMachine == null) {
                localVirtualMachine = attach(pid);
                if (localVirtualMachine != null) {
                    virtualMachineMap.put(pid, localVirtualMachine);
                }
            }
        } catch (Exception localException) {
            String localObject = localException.getMessage();
            if (localObject != null) {
                System.err.println(pid + ": " + (String) localObject);
            } else {
                localException.printStackTrace();
            }
            if (((localException instanceof AttachNotSupportedException)) && (loadSAClass() != null)) {
                System.err.println("The -F option can be used when the target process is not responding");
            }
            return "";
        }

        if (localVirtualMachine == null) {

            return "";
        }

        InputStream localInputStream =
                ((HotSpotVirtualMachine) localVirtualMachine).remoteDataDump((Object[]) paramArrayOfString);

        StringBuffer result = new StringBuffer();
        byte[] localObject = new byte['Ā'];
        int i;
        do {
            i = localInputStream.read(localObject);
            if (i > 0) {
                String str = new String(localObject, 0, i, "UTF-8");
                result.append(str);
            }
        } while (i > 0);

        localInputStream.close();
        String r = result.toString();
        result.setLength(0);
        return r;
    }

    @SuppressWarnings("rawtypes")
    private Class loadSAClass() {
        try {
            return Class.forName("sun.jvm.hotspot.tools.JStack", true, ClassLoader.getSystemClassLoader());
        } catch (Exception localException) {
        }
        return null;
    }

    @Override
    public void startedVm(Set<Integer> vmPids) {
        //这里只要一监听有新的虚拟机进程启动，就初始化好一个 ProcessInfo
        for (Integer pid : vmPids) {
            try {
                MonitoredVm localMonitoredVm = null;
                String str1 = String.format(pidMode, pid);
                ProcessInfo tmpprocessInfo = new ProcessInfo(jdkVersion, localIp);

                VmIdentifier localVmIdentifier = new VmIdentifier(str1);
                localMonitoredVm = ((MonitoredHost) monitoredHost).getMonitoredVm(localVmIdentifier, 0);
                tmpprocessInfo.setPid(pid);
                tmpprocessInfo.setMain(MonitoredVmUtil.mainClass(localMonitoredVm, jpsArguments.showLongPaths()));
                tmpprocessInfo.setMainArgs(MonitoredVmUtil.mainArgs(localMonitoredVm));
                tmpprocessInfo.setJvmArgs(MonitoredVmUtil.jvmArgs(localMonitoredVm));
                tmpprocessInfo.setJvmFlags(MonitoredVmUtil.jvmFlags(localMonitoredVm));
                ((MonitoredHost) monitoredHost).detach(localMonitoredVm);

                if (processInfoFilter.accept(tmpprocessInfo)) {
                    processInfosMap.put(str1, tmpprocessInfo);
                }
                virtualMachineMap.put(String.valueOf(pid), attach(String.valueOf(pid)));
                logger.error("startedVm:[{}]:[{}]", pid, virtualMachineMap.get(String.valueOf(pid)));
            } catch (URISyntaxException e) {
                logger.error("startedVm:pid:[{}]:URISyntaxException:[{}]", pid, e);
            } catch (MonitorException e) {
                logger.error("startedVm:pid:[{}]:MonitorException:[{}]", pid, e);
            } catch (Exception e) {
                logger.error("startedVm:pid:[{}]:Exception[{}]", pid, e);
            }
        }
    }

    private VirtualMachine attach(String pid) {
        try {
            return VirtualMachine.attach(pid);
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void terminatedVm(Set<Integer> vmPids) {
        //1、
        for (Integer pid : vmPids) {
            //走到这里，说明当前这个 process info 下线了，忽略 用户删除 jvm 临时目录的情况
            String str1 = String.format(pidMode, pid);
            processInfosMap.remove(str1);

            //2、
            for (Field field : jstatInfoFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                String name = field.getName().toLowerCase();
                String key = getJstatArguKey(name, String.valueOf(pid));
                JstatMonitoredWapper wapper = jstatMonitoredWapperMap.remove(key);
                if (wapper != null) {
                    wapper.helpGC();
                    wapper = null;
                }
            }

            //3、
            VirtualMachine virtualMachine = virtualMachineMap.remove(String.valueOf(pid));
            try {
                virtualMachine.detach();
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void init() {
        if (!isInit.compareAndSet(false, true)) {
            //更新失败，说明已经init 了
            return;
        }

        // 1、
        jstatInfoFields = JstatInfo.class.getDeclaredFields();
        // 2、
        String[] jpsArgs = {"-mlvV"};
        jpsArguments = new sun.tools.jps.Arguments(jpsArgs);
        //3、
        activeVmEventListener = new ActiveVmEventListener();

        HostIdentifier hostIdentifier = jpsArguments.hostId();
        try {
            monitoredHost = MonitoredHost.getMonitoredHost(hostIdentifier);
            //监控本地 vm 状态
            monitoredHost.addHostListener((HostListener) new HostListener() {
                //注意：如果有新的虚拟机上线 或者下线，都会触发这里的事件回调
                @SuppressWarnings("unchecked")
                public void vmStatusChanged(VmStatusChangeEvent paramAnonymousVmStatusChangeEvent) {
                    if (paramAnonymousVmStatusChangeEvent == null) {
                        return;
                    }

                    //1、在这里处理本地 虚拟机进程上下线情况
                    Set<Integer> activeVms = paramAnonymousVmStatusChangeEvent.getActive();
                    if (activeVms.size() > 0) {
                        activeVmEventListener.onEvent(activeVms);
                    }
                    //2、这里是监听到有新上线的 vm 进程
                    Set<Integer> startedVms = paramAnonymousVmStatusChangeEvent.getStarted();
                    if (startedVms.size() > 0) {
                        startedVm(startedVms);
                    }
                    //3、这里是刚下线的 vm 进程
                    Set<Integer> terminatedVms = paramAnonymousVmStatusChangeEvent.getTerminated();
                    if (terminatedVms.size() > 0) {
                        terminatedVm(terminatedVms);
                    }
                }

                public void disconnected(HostEvent paramAnonymousHostEvent) {
                    //nothing to do
                }
            });
        } catch (MonitorException e) {
        }
    }

    @Override
    public void registerProcessInfoFilter(IProcessInfoFilter processInfoFilter) {

        this.processInfoFilter = processInfoFilter;
    }

    @Override
    public void registerClassNameFilter(IClassNameFilter classNameFilter) {
        this.classNameFilter = classNameFilter;
    }

    public String getJstatArguKey(String name, String pid) {

        return name + "_" + pid;
    }

    public class ActiveVmEventListener implements IVmEventListener {

        @Override
        public void onEvent(Set<Integer> vmPids) {
            System.err.println("all active vm=" + vmPids.toString());
        }
    }
}
