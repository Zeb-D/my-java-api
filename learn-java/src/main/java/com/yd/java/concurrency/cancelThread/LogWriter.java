package com.yd.java.concurrency.cancelThread;

import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 不支持关闭的生产者-消费者日志服务
 * @author Yd on  2018-05-02
 * @description
 **/
public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread log;

    private boolean isShutdown;
    private int reservation;

    public LogWriter(PrintWriter writer){
        this.queue = new ArrayBlockingQueue<String>(16);
        this.log = new LoggerThread(writer);
    }

    public void start(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){

                    LogWriter.this.start();

                //otherthing todo
            }
        });
        log.start();
    }

    public void stop(){
        synchronized (this){
            isShutdown = true;
        }
        log.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    class LoggerThread extends Thread{
        private final PrintWriter writer;
        public LoggerThread(PrintWriter writer){
            this.writer = writer;
        }
        public void run(){
            try{
                while (true) {
                    try{
                        synchronized (LogWriter.this){
                            if (isShutdown && reservation ==0){
                                break;
                            }
                        }
                        String msg=queue.take();
                        synchronized (LogWriter.this){
                            --reservation;
                        }
                        writer.println(msg);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            } finally {
                writer.close();
            }
        }
    }
}


