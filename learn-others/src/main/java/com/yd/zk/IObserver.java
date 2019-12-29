package com.yd.zk;

public interface IObserver {
    void notified(String key, String value);
}
