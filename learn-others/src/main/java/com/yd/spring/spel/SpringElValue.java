package com.yd.spring.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SpEl表达式 内容赋值给属性
 *
 * @author Yd on  2018-06-27
 * @description
 **/
@Component
@PropertySource({"classpath:application.properties"})
public class SpringElValue {
    //${…}用法
    //{}里面的内容必须符合SpEL表达式，详细的语法，以后可以专门开新的文章介绍， 通过@Value(“${spelDefault.value}”)可以获取属性文件中对应的值，
    // 但是如果属性文件中没有这个属性，则会报错。可以通过赋予默认值解决这个问题，如@Value("${spelDefault.value:127.0.0.1}")
      @Value("${spelDefault.value}")
      private String spelDefault2;// 如果属性文件没有spelDefault.value，则会报错

    // 使用default.value设置值，如果不存在则使用默认值
    @Value("${spelDefault.value:127.0.0.1}")
    private String spelDefault;

    //#{…}用法
    // SpEL：调用字符串Hello World的concat方法
    @Value("#{'Hello World'.concat('!')}")
    private String helloWorld;

    // SpEL: 调用字符串的getBytes方法，然后调用length属性
    @Value("#{'Hello World'.bytes.length}")
    private String helloWorldbytes;

    //${…}和#{…}混合使用
    //#{}外面，${}在里面可以执行成功，那么反过来 ${}在外面，#{}在里面 是不可以，报错
    // SpEL: 注意不能反过来${}在外面，#{}在里面，这个会执行失败
    @Value("${#{'HelloWorld'.concat('_')}}")
    private List<String> servers2;
}
