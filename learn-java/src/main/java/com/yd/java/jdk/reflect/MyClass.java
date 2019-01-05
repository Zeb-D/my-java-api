package com.yd.java.jdk.reflect;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    protected List<String> stringList = new ArrayList<>();

    public List<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(List<String> list) {
        this.stringList = list;
    }
}