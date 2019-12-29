package com.yd.java.jvm;

import java.util.EventListener;
import java.util.Set;

/**
 * @author Zeb灬D
 */
public interface IVmEventListener extends EventListener {

    public void onEvent(Set<Integer> vmPids);
}
