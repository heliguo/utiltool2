package com.example.annotationlibrary.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:lgh on 2019-09-18 15:20
 */
@Target(ElementType.METHOD)//方法之上
@Retention(RetentionPolicy.RUNTIME)
@EventsBase(listenerSetter = "setOnLongClickListener",listenerType = View.OnLongClickListener.class,callBackListener = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
