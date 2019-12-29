package com.yd.java.jvm;

import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.tools.jstat.Arguments;

public class JstatMonitoredWapper implements IHelpGC {

    private MonitoredHost monitoredHost;
    private MonitoredVm monitoredVm;
    private Arguments arguments;

    public JstatMonitoredWapper() {
    }

    public JstatMonitoredWapper(MonitoredHost monitoredHost, MonitoredVm monitoredVm) {
        super();
        this.monitoredHost = monitoredHost;
        this.monitoredVm = monitoredVm;
    }


    public MonitoredHost getMonitoredHost() {
        return monitoredHost;
    }

    public void setMonitoredHost(MonitoredHost monitoredHost) {
        this.monitoredHost = monitoredHost;
    }

    public MonitoredVm getMonitoredVm() {
        return monitoredVm;
    }

    public void setMonitoredVm(MonitoredVm monitoredVm) {
        this.monitoredVm = monitoredVm;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public void helpGC() {
        arguments = null;
        monitoredVm = null;
        monitoredHost = null;
    }

}
