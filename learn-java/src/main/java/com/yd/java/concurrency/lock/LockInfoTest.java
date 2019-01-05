package com.yd.java.concurrency.lock;

import sun.management.LockInfoCompositeData;

import javax.management.openmbean.CompositeData;
import java.lang.management.LockInfo;

/**
 * @author Yd on  2018-06-11
 * @description
 **/
public class LockInfoTest {
    public static void main(String[] args) {
        String name = "大陆";
        LockInfo lockInfo = new LockInfo(name.getClass().getName(), System.identityHashCode(name));

        CompositeData lockInfoCompositeData = LockInfoCompositeData.toCompositeData(lockInfo);

        System.out.println(lockInfo);
        lockInfo = LockInfo.from(lockInfoCompositeData);
        System.out.println("after:" + lockInfo);
    }
}
