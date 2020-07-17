package com.example.annotationlibrary.annotations;

import androidx.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:lgh on 2019-09-18 11:26
 */
@Target(ElementType.TYPE)//该注解只能作用在类之上
@Retention(RetentionPolicy.RUNTIME)//jvm 在运行时通过反射技术，获取注解值
public @interface ContentView {
    @LayoutRes int value();//返回int类型的布局
}
