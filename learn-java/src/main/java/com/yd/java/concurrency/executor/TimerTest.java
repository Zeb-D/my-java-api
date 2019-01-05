package com.yd.java.concurrency.executor;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Yd on  2018-02-09
 * @description Timer工具具有 管理任务的延迟执行 以及 周期性 执行，
 * 但会出现线程泄漏：TimerTask 发生未检查性的异常，Timer并不捕获异常，将导致整个Timer都关闭；
 * 也会出现 单个TimerTask 执行时间 超出 Timer设置的时间，可能会导致 该TimerTask 少调用，或者 其它的TimerTask 的时间度 会受到影响
 * 请使用 Executors.newScheduledThreadPool 来代替它
 **/
public class TimerTest {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(),5);
        timer.schedule(new ThrowTask(),1);
//        Future  Callable
    }


    private static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            Boolean a = random.nextBoolean();
            if (!a){
                throw new RuntimeException("ThrowTask is Exception!"+a);
            }

        }
    }
}
