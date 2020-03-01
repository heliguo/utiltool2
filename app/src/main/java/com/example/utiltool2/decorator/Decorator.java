package com.example.utiltool2.decorator;

/**
 * author:lgh on 2020-01-10 14:39
 *  装饰类
 */
public abstract class Decorator extends Coffee {

    //需要装饰的类必须实现的方法
    public abstract String getDescriptionString();

}
