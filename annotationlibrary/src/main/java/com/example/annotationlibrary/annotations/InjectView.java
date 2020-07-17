package com.example.annotationlibrary.annotations;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:lgh on 2019-09-18 14:36
 */
@Target(ElementType.FIELD)//该注解只能作用在属性之上
@Retention(RetentionPolicy.RUNTIME)//jvm 在运行时通过反射技术，获取注解值
public @interface InjectView {
    @IdRes int value();
}
