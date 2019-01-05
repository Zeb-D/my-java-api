package com.yd.annotation;

import java.lang.annotation.*;

/**
 * 字段描述
 */
@Target({ElementType.FIELD,ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ColumnDesc {

    /**
     * 字段描述
     * @return
     */
    String columnDesc() default "";
}
