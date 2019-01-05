package com.yd.springmvc.noxml.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
//扫描控制器
@ComponentScan(includeFilters=@ComponentScan.Filter(type= FilterType.ANNOTATION,value=Controller.class))
public class SpringMvcConf extends WebMvcConfigurerAdapter {
 
 //配置视图解析器
 //html解析
 @Bean
 public ViewResolver htmlResolver(){
   InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
   viewResolver.setPrefix("/WEB-INF/view/");
   viewResolver.setSuffix(".html");
   return viewResolver;
 }
 //静态资源处理
 //当DisptacherServlet接收到了他匹配的请求，但是找不到相应的Controller，就会把这个请求返回给默认的处理（比如交给tomcat处理）
 @Override
 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
   configurer.enable();
 }
}