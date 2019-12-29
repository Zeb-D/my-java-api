package com.yd.java.jvm;

import com.yd.java.jvm.entity.ProcessInfo;

import java.util.List;

public class JvmMonitorManager {
    //constant
    private static IJvmMonitor jvmMonitor;

    public final static void init(IJvmMonitor ijvmMonitor, IProcessInfoFilter processInfoFilter, IClassNameFilter classNameFilter) {
        if (jvmMonitor == null) {
            synchronized (IJvmMonitor.class) {
                if (jvmMonitor == null) {
                    jvmMonitor = ijvmMonitor;
                    jvmMonitor.init();

                    jvmMonitor.registerProcessInfoFilter(processInfoFilter);
                    jvmMonitor.registerClassNameFilter(classNameFilter);
                }
            }
        }
    }

    public static List<ProcessInfo> getAllProcessInfo() throws Exception {

        return jvmMonitor.getAllProcessInfo();
    }

    public static List<ProcessInfo> getAllProcessInfoWithCollector() throws Exception {
        List<ProcessInfo> processInfos = JvmMonitorManager.getAllProcessInfo();
        JvmMonitorManager.pendingJstatInfo(processInfos);
        JvmMonitorManager.jmapHisto(processInfos, false);
        JvmMonitorManager.jstackInfo(processInfos);
        return processInfos;
    }

    /**
     * @param processInfos
     */
    public static void pendingJstatInfo(List<ProcessInfo> processInfos) {

        jvmMonitor.pendingJstatInfo(processInfos);
    }

    /**
     * @param pid
     * @param liveOrAll
     */
    public static void jmapHisto(List<ProcessInfo> processInfos, boolean liveOrAll) {

        jvmMonitor.jmapHisto(processInfos, liveOrAll);
    }

    public static void jstackInfo(List<ProcessInfo> processInfos) {

        jvmMonitor.jstackInfo(processInfos);
    }

}
