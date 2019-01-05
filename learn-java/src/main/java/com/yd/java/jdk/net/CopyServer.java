package com.yd.java.jdk.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class CopyServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(10004);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        System.out.println(ip + "....is connected!");
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(new FileWriter("server.txt"));
        String line = null;
        while ((line = bufIn.readLine()) != null) {
            System.out.println(line);
            pw.println(line);
        }
        pw.flush();
        pw.close();

        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.write("上传成功");
        System.out.println("---------");
        out.flush();
        out.close();
        Thread.sleep(10000);
        ss.close();
    }
}  