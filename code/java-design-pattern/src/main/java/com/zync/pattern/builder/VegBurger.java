package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 素食汉堡
 * @date 2019/5/11 15:13
 */
public class VegBurger extends AbstractBurger {
    @Override
    public String name() {
        return "素食汉堡";
    }

    @Override
    public float price() {
        return 25.3f;
    }
}
