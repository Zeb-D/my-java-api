package com.yd.java.jvm;

import com.yd.java.jvm.entity.ProcessInfo;

import java.util.List;
import java.util.Set;

public interface IJvmMonitor {

    /**
     * 初始化的做一些事情
     */
    public void init();

    public List<ProcessInfo> getAllProcessInfo();

    public void pendingJstatInfo(List<ProcessInfo> processInfos);

    public void jmapHisto(List<ProcessInfo> processInfos, boolean liveOrAll);

    public void jstackInfo(List<ProcessInfo> processInfos);

    /**
     * 当本地启动一个jvm 时，回调的方法，在这个方法里面会初始化一个 表示这个进程基本信息的实例
     *
     * @return
     */
    public void startedVm(Set<Integer> vmPids);

    /**
     * 当本地一个jvm terminated 时，回调这个方法做一些善后的处理
     */
    public void terminatedVm(Set<Integer> vmPids);

    /**
     * @param processInfoFilter
     */
    public void registerProcessInfoFilter(IProcessInfoFilter processInfoFilter);

    /**
     * @param classNameFilter
     */
    public void registerClassNameFilter(IClassNameFilter classNameFilter);
}
