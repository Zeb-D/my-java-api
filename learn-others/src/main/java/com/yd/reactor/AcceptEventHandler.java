package com.yd.reactor;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Zeb灬D on 2018/3/6
 * Description:
 */
@Data
@AllArgsConstructor
public class AcceptEventHandler extends EventHandler {

    private Selector selector;

    @Override
    public void handle(Event event) {
        //处理Accept的event事件
        if (event.getType() == EventType.ACCEPT) {

            //TODO 处理ACCEPT状态的事件

            //将事件状态改为下一个READ状态，并放入selector的缓冲队列中
            Event readEvent = new Event();
            readEvent.setSource(event.getSource());
            readEvent.setType(EventType.READ);
            System.out.println(event.toString());
            selector.addEvent(readEvent);
        }
    }

}