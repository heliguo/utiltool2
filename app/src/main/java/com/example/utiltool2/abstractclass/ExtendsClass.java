package com.example.utiltool2.abstractclass;

/**
 * author:lgh on 2019-12-18 08:58
 */
public class ExtendsClass extends TestAbstract {
    @Override
    public void test2() {

    }

    /**
     * 非抽象类可以选择性复写
     */

    @Override
    public void test1() {
        super.test1();
    }
}
