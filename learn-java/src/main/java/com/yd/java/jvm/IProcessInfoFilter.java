package com.yd.java.jvm;


import com.yd.java.jvm.entity.ProcessInfo;

public interface IProcessInfoFilter {

    public boolean accept(ProcessInfo processInfo);
}
