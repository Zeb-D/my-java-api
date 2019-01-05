package com.yd.java.jdk.io;

import com.yd.java.jdk.Constant;

import java.io.FileReader;
import java.io.RandomAccessFile;

/**
 * @author Yd on  2018-04-19
 * @description
 **/
public class FileReaderTest {

    public static void main(String[] args) throws Exception {
        StringBuffer sb = new StringBuffer();
        char[] buf = new char[1024];
        String fileName = Constant.TEMP;
        FileReader fileReader = new FileReader(fileName);
        while (fileReader.read(buf)>0){
            sb.append(buf);
        }
        sb.toString();

        fileName = "D:\\ChromeDownload\\ip2region.db";
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        String result = null;
        while ((result = file.readLine())!=null){
            System.out.println(result);
        }
    }
}
