package com.yd.java.jvm.entity;

import com.yd.java.jvm.IHelpGC;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcessInfo implements Serializable, IHelpGC {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String jdkVersion;
    private String localHost;
    private int pid;
    private String main;
    private String mainArgs;
    private String jvmArgs;//包括两部分，1、给主函数传参 2、给jvm 传参
    private String jvmFlags;
    private JstatInfo jstatInfo;
    private List<String> jmapHisto;//根据这里面的信息，可以追踪每个类的实例count and size.可以初步的发现某个类的 instance 泄漏了
    private JstackInfo jstackInfo;

    public ProcessInfo() {
    }

    public ProcessInfo(String jdkVersion, String localHost) {
        super();
        this.jdkVersion = jdkVersion;
        this.localHost = localHost;
    }

    public ProcessInfo(int pid, String main, String mainArgs, String jvmArgs, String jvmFlags, JstatInfo jstatInfo,
                       List<String> jmapHisto) {
        super();
        this.pid = pid;
        this.main = main;
        this.mainArgs = mainArgs;
        this.jvmArgs = jvmArgs;
        this.jvmFlags = jvmFlags;
        this.jstatInfo = jstatInfo;
        this.jmapHisto = jmapHisto;
    }


//	public String getMainArgs() {
//		return mainArgs;
//	}
//
//
//	public void setMainArgs(String mainArgs) {
//		this.mainArgs = mainArgs;
//	}
//
//
//	public String getJvmArgs() {
//		return jvmArgs;
//	}
//
//
//	public void setJvmArgs(String jvmArgs) {
//		this.jvmArgs = jvmArgs;
//	}
//
//
//	public String getJvmFlags() {
//		return jvmFlags;
//	}
//
//
//	public void setJvmFlags(String jvmFlags) {
//		this.jvmFlags = jvmFlags;
//	}
//
//
//	public int getPid() {
//		return pid;
//	}
//	public void setPid(int pid) {
//		this.pid = pid;
//	}
//	public String getMain() {
//		return main;
//	}
//	public void setMain(String main) {
//		this.main = main;
//	}
//
//	public JstatInfo getJstatInfo() {
//		return jstatInfo;
//	}
//
//	public void setJstatInfo(JstatInfo jstatInfo) {
//		this.jstatInfo = jstatInfo;
//	}
//
//	public List<String> getJmapHisto() {
//		return jmapHisto;
//	}
//
//	public void setJmapHisto(List<String> jmapHisto) {
//		this.jmapHisto = jmapHisto;
//	}
//
//	public JstackInfo getJstackInfo() {
//		return jstackInfo;
//	}
//
//	public void setJstackInfo(JstackInfo jstackInfo) {
//		this.jstackInfo = jstackInfo;
//	}
//
//	public String getJdkVersion() {
//		return jdkVersion;
//	}
//
//	public void setJdkVersion(String jdkVersion) {
//		this.jdkVersion = jdkVersion;
//	}
//
//	public String getLocalHost() {
//		return localHost;
//	}
//
//	public void setLocalHost(String localHost) {
//		this.localHost = localHost;
//	}

    @Override
    public String toString() {
        return "ProcessInfo [pid=" + pid + ", main=" + main + ", mainArgs=" + mainArgs + ", jvmArgs=" + jvmArgs
                + ", jvmFlags=" + jvmFlags + ", jstatInfo=" + jstatInfo + ", jmapHisto=" + jmapHisto + "]";
    }

    @Override
    public void helpGC() {
        jstatInfo = null;
        jmapHisto.clear();
        jmapHisto = null;
        jstackInfo.setThreadStackInfo(null);
        jstackInfo = null;
    }

}
