package com.yd.java.jvm.entity;

import java.io.Serializable;

/**
 * jstat 命令采集的数据
 *
 * @author Zeb灬D
 */
public class JstatInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String gc;//空格分割，有15个字段。分别表示了各个区的内存使用情况以及YGC,FGC 的情况
    private String gcCapacity;
    private String gcCause;
    private String gcNew;
    private String gcNewCapacity;
    private String gcOld;
    private String gcOldCapacity;
    private String gcPermCapacity;

	/*public String getGcMetacapacity() {
		return gcMetacapacity;
	}

	public void setGcMetacapacity(String gcMetacapacity) {
		this.gcMetacapacity = gcMetacapacity;
	}

	private String gcMetacapacity;*/

    private String gcUtil;

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public String getGcCapacity() {
        return gcCapacity;
    }

    public void setGcCapacity(String gcCapacity) {
        this.gcCapacity = gcCapacity;
    }

    public String getGcCause() {
        return gcCause;
    }

    public void setGcCause(String gcCause) {
        this.gcCause = gcCause;
    }

    public String getGcNew() {
        return gcNew;
    }

    public void setGcNew(String gcNew) {
        this.gcNew = gcNew;
    }

    public String getGcNewCapacity() {
        return gcNewCapacity;
    }

    public void setGcNewCapacity(String gcNewCapacity) {
        this.gcNewCapacity = gcNewCapacity;
    }

    public String getGcOld() {
        return gcOld;
    }

    public void setGcOld(String gcOld) {
        this.gcOld = gcOld;
    }

    public String getGcOldCapacity() {
        return gcOldCapacity;
    }

    public void setGcOldCapacity(String gcOldCapacity) {
        this.gcOldCapacity = gcOldCapacity;
    }

    public String getGcUtil() {
        return gcUtil;
    }

    public void setGcUtil(String gcUtil) {
        this.gcUtil = gcUtil;
    }

    public String getGcPermCapacity() {
        return gcPermCapacity;
    }

    public void setGcPermCapacity(String gcPermCapacity) {
        this.gcPermCapacity = gcPermCapacity;
    }

}
