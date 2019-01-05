package com.yd.java.concurrency.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Yd on  2018-02-09
 * @description 支持关闭操作
 **/
public class LifecycleWebServer {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (!cachedThreadPool.isShutdown()){
            Socket socket = serverSocket.accept();
            try {
                cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("ip:"+socket.getRemoteSocketAddress());
                    }
                });
            }catch (RejectedExecutionException e){
                if (!cachedThreadPool.isShutdown()){
                    System.out.println("task submission rejected:"+e.getMessage());
                }
            }

        }

    }

    public void stop(){
        cachedThreadPool.shutdown();
    }


}
