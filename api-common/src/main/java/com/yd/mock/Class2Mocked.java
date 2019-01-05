package com.yd.mock;

/**
 * @author Yd on  2018-01-17
 * @Description：
 **/
public class Class2Mocked {
    public static int getDouble(int i){
        return i*2;
    }
    public String getTripleString(int i){
        return multiply3(i)+"";
    }
    private int multiply3(int i){
        return i*3;
    }
}
