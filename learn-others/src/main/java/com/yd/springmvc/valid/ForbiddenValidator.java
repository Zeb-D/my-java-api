package com.yd.springmvc.valid;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ForbiddenValidator implements ConstraintValidator<Forbidden, String> {//不一定是String
  
    private String[] forbiddenWords = {"admin"};  
  
    @Override  
    public void initialize(Forbidden constraintAnnotation) {  
        //初始化，得到注解数据  
    }  
  
    @Override  
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value)) {
            return true;  
        }  
  
        for(String word : forbiddenWords) {  
            if(value.contains(word)) {  
                return false;//验证失败  
            }  
        }  
        return true;  
    }  
} 