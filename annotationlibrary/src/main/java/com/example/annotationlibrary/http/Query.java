package com.example.annotationlibrary.http;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author:lgh on 2019-09-29 15:30
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
    String value();

}
