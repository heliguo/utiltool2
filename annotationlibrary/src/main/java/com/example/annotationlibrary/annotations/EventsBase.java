package com.example.annotationlibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:lgh on 2019-09-18 15:11
 */
@Target(ElementType.ANNOTATION_TYPE)//该注解作用在另外一个注解上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventsBase {
    //3个相同点

    //1、setxxxListener 方法名
    String listenerSetter();

    //2、监听对象（什么事件）
    Class<?> listenerType();

    //3、回调方法
    String callBackListener();
}
