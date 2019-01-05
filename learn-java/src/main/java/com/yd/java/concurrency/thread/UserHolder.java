package com.yd.java.concurrency.thread;

import com.yd.entity.User;

/**
 * @author Yd on  2018-01-22
 * @Description：
 **/
public class UserHolder {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal();

    public static void set(User user) {
        userThreadLocal.set(user);
    }

    public static User get() {
        return userThreadLocal.get();
    }

    public static void remove() {
        userThreadLocal.remove();
    }
}
