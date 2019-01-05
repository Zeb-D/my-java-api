package com.yd.springmvc.controller;

import com.yd.entity.User;
import com.yd.springmvc.validation.ValidationParameter;
import com.yd.springmvc.validation.ValidationService;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{application['export2Excel.fileName']}")
    private String fileName;

    @RequestMapping(value = "/sayHello/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public String sayHello(@PathVariable("id") Integer id, @RequestParam("name") String name, @RequestParam("age") String age) {
        return id + "sayHello " + name + " at " + age + fileName;
    }

    @RequestMapping(value = "/getUser/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id, @RequestBody User user) {
        System.out.println("\n----user:" + user);
        user.setId(RandomUtils.nextInt(1, 100));
        return user;
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.POST})
    @ResponseBody
    public void validate(@Validated(ValidationService.Save.class) @RequestBody ValidationParameter parameter, BindingResult result) {
        System.out.println("\n----user:" + parameter);
    }

}
