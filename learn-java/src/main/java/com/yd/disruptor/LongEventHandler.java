package com.yd.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by Zeb灬D on 2017/9/29
 * Description:
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event:" + event);
    }
}