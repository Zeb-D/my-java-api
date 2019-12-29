package com.yd.reactor;

import lombok.Data;

/**
 * Created by Zeb灬D on 2018/3/6
 * Description:
 */
@Data
public abstract class EventHandler {
    private InputSource source;

    public abstract void handle(Event event);

}