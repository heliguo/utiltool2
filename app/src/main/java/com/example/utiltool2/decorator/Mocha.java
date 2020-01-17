package com.example.utiltool2.decorator;

/**
 * author:lgh on 2020-01-10 14:42
 * 咖啡具体实现类 摩卡
 */
public class Mocha extends Coffee {

    public Mocha() {
        descriptionString = "MochaCoffee";
    }

    @Override
    public double cost() {
        return 15;
    }
}
