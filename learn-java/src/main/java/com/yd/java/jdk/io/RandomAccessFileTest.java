package com.yd.java.jdk.io;

import com.yd.java.jdk.Constant;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile允许你来回读写文件，也可以替换文件中的某些部分。FileInputStream和FileOutputStream没有这样的功能。
 *
 * @author Yd on 2018-06-20
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile(Constant.FILENAME, "rw");

        //在RandomAccessFile的某个位置读写之前，必须把文件指针指向该位置。
        // 通过seek()方法可以达到这一目标。可以通过调用getFilePointer()获得当前文件指针的位置。
        file.seek(200);
        long pointer = file.getFilePointer();
        System.out.println(pointer);

        //任何一个read()方法都可以读取RandomAccessFile的数据
        int aByte = file.read();//read()方法在读取完一个字节之后，会自动把指针移动到下一个可读字节。这意味着使用者在调用完read()方法之后不需要手动移动文件指针。
        System.out.println(aByte);

        //任何一个write()方法都可以往RandomAccessFile中写入数据
        file.write("Hello World".getBytes());

        file.close();
    }
}
