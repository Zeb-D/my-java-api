package com.yd.springmvc.controller;

import com.yd.entity.Employee;
import com.yd.entity.User;
import com.yd.springmvc.resolver.FormObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yd on  2018-01-23
 * @Description：
 **/
@Controller
@RequestMapping(value = "/foc")
public class FormObjController {

    @RequestMapping("/test1")
    public String test1(@FormObj User user, @FormObj Employee emp) {
        return "index";
    }

    @RequestMapping("/test2")
    public String test2(@FormObj("u") User user, @FormObj("e") Employee emp) {
        return "index";
    }

    @RequestMapping("/test3")
    public String test3(@FormObj(value = "u", show = false) User user, @FormObj("e") Employee emp) {
        return "index";
    }

}
