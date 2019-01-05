package com.yd.javaWeb.io;

import com.yd.java.jdk.Constant;

import java.io.*;

/**
 * 序列化的数据包括哪些
 *
 * @author Yd on  2018-05-14
 * @description
 **/
public class Serialize implements Serializable {
    private static final long serialVersionUID = 1L;
    public static int id = 5;
    public int num = 1994;

    public static void main(String[] args) {
        //把对象 写入文件
        try {
            FileOutputStream outputStream = new FileOutputStream(Constant.FILENAME);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            Serialize serializable = new Serialize();
            oos.writeObject(serializable);
            oos.flush();//从工作内存 刷新到磁盘中
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Serialize.id = 10;
        //读取二进制文件
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constant.FILENAME));
            Serialize s = (Serialize) ois.readObject();
            System.out.println(s.num);
            System.out.println("id:" + s.id);
            System.out.println(ois.available());//流数据读取一次就会被清空
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
