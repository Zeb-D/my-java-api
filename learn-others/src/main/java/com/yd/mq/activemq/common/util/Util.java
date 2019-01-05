package com.yd.mq.activemq.common.util;

import com.yd.mq.activemq.entity.User2;

/**
 * @author Yd on  2017-12-15
 * @Description：
 **/
public class Util {

    public static <T> T get(Object obj,T cls) {
        return (T)obj;
    }

    public static void main(String[] args) {
        User2 user = new User2(123);
        user.getClass();
        System.out.println(get(user,Object.class));
    }
}
