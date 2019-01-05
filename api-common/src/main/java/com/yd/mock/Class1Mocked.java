package com.yd.mock;

/**
 * @author Yd on  2018-01-17
 * @Description：
 **/
public class Class1Mocked {
    public  String hello(String name){
        System.out.println("hello "+name);
        return "hello "+name;
    }
    public void show(){
        System.out.println("Class1Mocked.show()");
    }

    public static void main(String[] args) {

    }
}
