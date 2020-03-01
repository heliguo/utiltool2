package com.example.utiltool2.decorator;

/**
 * author:lgh on 2020-01-10 14:44
 * 咖啡装饰类1 糖
 */
public class Sugar extends Decorator {

    Coffee coffee;
    double sugarCost = 2;

    //需要加糖的咖啡
    public Sugar(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescriptionString() {
        return coffee.getDescriptionString() + ",sugar";
    }

    //返回加糖后的咖啡价格
    @Override
    public double cost() {
        return coffee.cost() + sugarCost;
    }
}
