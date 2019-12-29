package com.yd.java.jvm;


import com.yd.java.jvm.entity.ProcessInfo;

public class DefaultProcessFilter implements IProcessInfoFilter {

    @Override
    public boolean accept(ProcessInfo processInfo) {

        return !(processInfo.getMain() == null || processInfo.getMain().trim().length() == 0);
    }

}
