package com.yd.reactor;

import lombok.Data;

/**
 * Created by Zeb灬D on 2018/3/6
 * Description:
 */
@Data
public class Event {
    private InputSource source;
    private EventType type;

    public String toString() {
        return "type:" + type.name() + " source:" + source.toString();
    }
}