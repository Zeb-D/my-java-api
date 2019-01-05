package com.yd.akka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yd on  2018-02-03
 * @Description：将SetRequest 消息实现为一个不可变对象
 **/
@Data
public class SetRequest {
    private final String key;
    private final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
