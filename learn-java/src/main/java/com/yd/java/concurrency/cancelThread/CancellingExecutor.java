package com.yd.java.concurrency.cancelThread;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 停止基于线程的服务
 * @author Yd on  2018-05-02
 * @description
 **/
public class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected<T> RunnableFuture<T> newTaskFor(Callable<T> callable){
        if (callable instanceof CancelableTask){
            return ((CancelableTask) callable).newTask();
        }else return super.newTaskFor(callable);
    }

}

interface CancelableTask<T> extends  Callable<T>{
    void cancel();
    RunnableFuture<T> newTask();
}

abstract class SocketUsingTask<T> implements CancelableTask<T>{
    private Socket socket;

    protected synchronized void setSocket(Socket socket){
        this.socket=socket;
    }

    public synchronized void cancel(){
        try{
            if (socket!=null)socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            //ignored
        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
