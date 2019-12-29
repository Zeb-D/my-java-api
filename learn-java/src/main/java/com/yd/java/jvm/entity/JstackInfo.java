package com.yd.java.jvm.entity;

import java.io.Serializable;

public class JstackInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String threadStackInfo;

    public JstackInfo() {
    }

    public JstackInfo(String threadStackInfo) {
        super();
        this.threadStackInfo = threadStackInfo;
    }

    public String getThreadStackInfo() {
        return threadStackInfo;
    }

    public void setThreadStackInfo(String threadStackInfo) {
        this.threadStackInfo = threadStackInfo;
    }

}
