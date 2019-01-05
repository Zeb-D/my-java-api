package com.yd.java.jdk;

/**
 * 存放一些通用的变量
 *
 * @author Yd on  2018-06-22
 * @description
 **/
public class Constant {
    public static final String USR_DIR = System.getProperty("user.dir");
    public static final String FILENAME = USR_DIR + "/Serialize.dat";
    public static final String TEMP = USR_DIR+"/system.out.txt";

    static {
        System.out.println("USER_DIR >>> " + USR_DIR);
        System.out.println("FILENAME >>> " + FILENAME);
    }
}
