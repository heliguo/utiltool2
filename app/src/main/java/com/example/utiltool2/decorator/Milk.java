package com.example.utiltool2.decorator;

/**
 * author:lgh on 2020-01-10 14:52
 * 咖啡装饰类2 牛奶
 */
public class Milk extends Decorator {

    private Coffee coffee;
    private double milkCost = 3;

    public Milk(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescriptionString() {
        return coffee.getDescriptionString() + ",milk";
    }

    @Override
    public double cost() {
        return coffee.cost() + milkCost;
    }
}
