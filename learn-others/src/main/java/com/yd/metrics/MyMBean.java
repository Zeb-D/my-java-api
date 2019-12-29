package com.yd.metrics;

/**
 * Created by Zeb灬D on 2017/11/27
 * Description:
 */
public interface MyMBean {

    public void start();

    public void stop();

    public String getName();

    public void setName(String name);

}