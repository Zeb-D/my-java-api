package com.yd.javaWeb.io;

import java.io.*;

/**
 * @author Yd on  2018-05-14
 * @description
 **/
public class IOTest {
    //1、基于字节io
    OutputStream out = new BufferedOutputStream(new ObjectOutputStream(new FileOutputStream("fileName")));

    Writer writer = new CharArrayWriter();

    File file = new File("fileName");

    public void testFile() throws FileNotFoundException {
        FileInputStream in = new FileInputStream("fileName");
    }

    public void testReader(){
        try{
            StringBuffer sb = new StringBuffer();
            char[] buf = new char[1024];
            FileReader f = new FileReader("fileName");
            while (f.read(buf)>0){
                sb.append(buf);
            }
            sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public IOTest() throws IOException {
    }
}
