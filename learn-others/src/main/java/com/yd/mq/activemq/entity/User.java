package com.yd.mq.activemq.entity;

/**
 * @author Yd on  2017-12-15
 * @Description：
 **/
public class User {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
