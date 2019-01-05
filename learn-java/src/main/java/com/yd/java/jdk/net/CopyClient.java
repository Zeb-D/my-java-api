package com.yd.java.jdk.net;

import com.yd.java.jdk.Constant;

import java.io.*;
import java.net.Socket;

/*
TCP复制文件 
Socket类的方法（用于加结束标记）： 
 void shutdownInput()  
          此套接字的输入流置于“流的末尾”。  
 void shutdownOutput()  
          禁用此套接字的输出流。  
注：用over作标记容易重复 
*/
class CopyClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 10004);
        //读取一个文件。
        BufferedReader bufr =
                new BufferedReader(new FileReader(Constant.TEMP));
        PrintWriter out = new PrintWriter(s.getOutputStream());
        String line = null;
        while ((line = bufr.readLine()) != null) {
            System.out.println(line);
            out.println(line);
        }
        out.flush();
        out.close();
        BufferedReader bufIn =new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = bufIn.readLine();
        System.out.println(str);
        s.close();
    }
}  
