package com.yd.springmvc.noxml.controller;

import com.yd.entity.User;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zouYd
 */
@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("#{application['user.id']}")
    private String age ="1";

    @RequestMapping(value = "/sayHello/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public String sayHello(@PathVariable("id") Integer id, @RequestParam("name") String name, @RequestParam("age") String age) {
        return id + "sayHello " + name + " id " + id + "age" + age;
    }

    @RequestMapping(value = "/getUser/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id, @RequestBody User user) {
        System.out.println("\n----user:" + user);
        user.setId(RandomUtils.nextInt(1, 100));
        return user;
    }

}
