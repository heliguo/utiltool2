package com.example.utiltool2.decorator;

/**
 * author:lgh on 2020-01-10 14:35
 * 咖啡父类
 */
public abstract class Coffee {

    String descriptionString = "未知类型";

    //产品描述
    public String getDescriptionString() {
        return descriptionString;
    }

    //子类需要实现的方法，价钱
    public abstract double cost();

}
