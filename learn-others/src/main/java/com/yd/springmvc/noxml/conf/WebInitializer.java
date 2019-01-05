package com.yd.springmvc.noxml.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //得到中间层(service、dao、aop、po等)的配置
    //Spring配置，得到bean
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    //得到controler和ViewResolver的配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConf.class};
    }

    //标识哪些url要经过这个DisptacherServlet处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};//所有url都被DisptacherServlet处理
    }

}