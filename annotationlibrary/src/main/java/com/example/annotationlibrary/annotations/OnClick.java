package com.example.annotationlibrary.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:lgh on 2019-09-18 15:17
 */
@Target(ElementType.METHOD)//方法之上
@Retention(RetentionPolicy.RUNTIME)
@EventsBase(listenerSetter = "setOnClickListener",listenerType = View.OnClickListener.class,callBackListener = "onClick")
public @interface OnClick {
    int[] value();
}
