package com.yd.java.jvm;

import com.yd.java.jvm.entity.ProcessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ElogJvmMontiorSource {
    private final static Logger logger = LoggerFactory.getLogger(ElogJvmMontiorSource.class);

    private final static AtomicInteger count = new AtomicInteger(0);

    private static ScheduledExecutorService schedulerExecutorService = null;

    /**
     * 解析完 source 后，会将解析的key value pair 传入给指定的业务实现接口。
     * 例如：如果你配置的source 是这样子的：
     * <p>
     * a1.sources.jvm.type = source.ElogJVMMontiorSource
     * a1.sources.jvm.channels = c1
     */

    public static void main(String[] args) {
        ElogJvmMontiorSource source = new ElogJvmMontiorSource();
        source.start();
    }

    public synchronized void start() {

        JvmMonitorManager.init(new JdkToolsJvmMonitorImpl(), new DefaultProcessFilter(), new ClassNameFilter());
        initSchedulerExecutor();
        schedulerExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                collectJvmProcessInfo();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void initSchedulerExecutor() {
        if (schedulerExecutorService != null) {
            return;
        }
        schedulerExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "jvm-schuduler-executor-" + count.incrementAndGet());
                if (thread.isDaemon()) {
                    thread.setDaemon(false);
                }

                if (thread.getPriority() != 5) {
                    thread.setPriority(5);
                }

                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("jvm-monitor-scheduler", e);
                        if (t.isInterrupted() && !Thread.interrupted()) {
                            t.interrupt();
                        }
                    }
                });

                return thread;
            }
        });
    }

    public void collectJvmProcessInfo() {
        try {
            List<ProcessInfo> processInfos = JvmMonitorManager.getAllProcessInfo();
            try {
                JvmMonitorManager.pendingJstatInfo(processInfos);
                JvmMonitorManager.jmapHisto(processInfos, false);
                JvmMonitorManager.jstackInfo(processInfos);

                for (ProcessInfo processInfo : processInfos) {
//					SimpleEvent simpleEvent = new SimpleEvent();
//					HashMap<String, String> headers = new HashMap<>();
//					headers.put(KafkaSinkConstants.TOPIC_HEADER, "jvmMonitorTopic");
//					headers.put(KafkaSinkConstants.KEY_HEADER, processInfo.getLocalHost());
//					String result = GSON.toJson(processInfo);
//					logger.info("ElogJvmMontiorSource:[{}]",result);
                    //System.err.println("orginal---->"+result.getBytes(Charset.forName("utf-8")).length + " Byte");
                    //数据量可能过大，压缩传输,
//					byte[] resultB = ZLibUtils.compress(result.getBytes(Charset.forName("utf-8")));
                    //System.err.println("one-->"+resultB.length + " Byte");
//					simpleEvent.setBody(resultB);
                    //simpleEvent.setBody(result.getBytes(Charset.forName("utf-8")));
//					simpleEvent.setHeaders(headers);
//					channelProcessor.processEvent(simpleEvent);


                }
            } finally {
                for (ProcessInfo processInfo : processInfos) {
                    processInfo.helpGC();
                }

                processInfos.clear();
                processInfos = null;
            }
        } catch (Exception e) {
        }
    }

    public static class ProcessFilter implements IProcessInfoFilter {

        @Override
        public boolean accept(ProcessInfo processInfo) {

            return !(processInfo.getMain() == null || processInfo.getMain().trim().length() == 0);
        }
    }

    public static class ClassNameFilter implements IClassNameFilter {

        @Override
        public boolean accept(String line) {
            //line.contains("com.hqyg") || line.contains("com.globalegrow") || line.contains("globalegrow") || line.contains("hqyg")
            return true;
        }
    }
}
