package com.yd.java.java8;

import com.yd.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Yd on  2018-04-27
 * @description
 **/
public class HelloStream {
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        users.add(new User());
        long count = users.stream().filter(user -> "Yd".equals(user.getName())).count();
        System.out.println(count);

        User user = users.stream().min(Comparator.comparing(user1 -> user1.getId())).get();

        int sum = Stream.of(1, 3, 5).reduce(0, (a, b) -> a + b);
        System.out.println(sum);
//        Iterator<User> iterable =  users.iterator();
//        StreamSupport.stream()
    }
}
