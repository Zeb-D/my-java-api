package com.yd.zk;

/**
 * Created by Zeb灬D on 2018/1/2
 * Description:
 */
public class TestZkConfig {
    public static void main(String[] args) {
        ZkConfigProfile zkConfigProfile = new ZkConfigProfile("10.40.6.154:2181", "/elog/erms");
        ZkConfigMap zkConfigMap = new ZkConfigMap(zkConfigProfile, "/ds");
        zkConfigMap.registerListener(new IObserver() {
            @Override
            public void notified(String key, String value) {
                System.out.println(key + " " + value);
            }
        });

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}