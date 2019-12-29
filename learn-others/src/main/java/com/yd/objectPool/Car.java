package com.yd.objectPool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Zeb灬D on 2018/4/10
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String name;
    private int price;

    @Override
    public String toString() {
        return "name:" + name + " price:" + price;
    }
}