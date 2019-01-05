package com.yd.entity;

import lombok.Data;

/**
 * @author Yd on  2018-02-12
 * @description 测试 继承树 相关数据
 **/
@Data
public class A {
    private String name;
}

@Data
class B extends A {
    private Integer age;
}

@Data
class C extends A {
    private Integer id;
}
