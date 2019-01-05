package com.yd.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.*;

/**
 * @author Yd on  2018-04-02
 * @description
 **/
public class HelloCamel {
    public static void main(String[] args) throws Exception {
        //camel拷贝文件
        camelCopyFile();

        //普通拷贝文件
//        commonCopyFile();
    }

    public static void camelCopyFile() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:D:/data/inbox?noop=true")
                        .filter().xpath("/order[not(@test)]")
                        .to("file:D:/data/outbox");
            }
        });
        camelContext.start();
        Thread.sleep(1000);
        camelContext.stop();
    }

    public static void commonCopyFile() throws IOException {
        File inboxDirectory = new File("D:/data/inbox");
        File outboxDirectory = new File("D:/data/outbox");

        outboxDirectory.mkdir();
        File[] files = inboxDirectory.listFiles();
        for (File source : files) {
            if (source.isFile()) {
                File dest = new File(
                        outboxDirectory.getPath()
                                + File.separator
                                + source.getName());
                copyFile(source, dest);
            }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        OutputStream out = new FileOutputStream(dest);
        byte[] buffer = new byte[(int) source.length()];
        FileInputStream in = new FileInputStream(source);
        in.read(buffer);
        try {
            out.write(buffer);
        } finally {
            out.close();
            in.close();
        }
    }
}
