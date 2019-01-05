package com.yd.springmvc.noxml.conf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexServlet {
    @RequestMapping(value = "/", method = RequestMethod.GET)//GET方法访问"/"的时候，调用此方法
    public String get(Model model) {
        model.addAttribute("title", "index");
        return "index";
    }
}