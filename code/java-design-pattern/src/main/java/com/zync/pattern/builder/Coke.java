package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 可口可乐
 * @date 2019/5/11 15:16
 */
public class Coke extends AbstractColdDrink {

    @Override
    public String name() {
        return "可口可乐";
    }

    @Override
    public float price() {
        return 8.8f;
    }
}
