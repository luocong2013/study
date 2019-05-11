package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 鸡肉汉堡
 * @date 2019/5/11 15:14
 */
public class ChickenBurger extends AbstractBurger {

    @Override
    public String name() {
        return "鸡肉汉堡";
    }

    @Override
    public float price() {
        return 67.9f;
    }
}
