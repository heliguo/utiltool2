package com.example.utiltool2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lgh on 2019-11-06 10:22
 */
@Target(ElementType.METHOD)//方法上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface LogRecord {
    String value() default "默认需求，用户没有填，我也没方法";
}
