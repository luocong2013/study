package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 百事可乐
 * @date 2019/5/11 15:16
 */
public class Pepsi extends AbstractColdDrink {

    @Override
    public String name() {
        return "百事可乐";
    }

    @Override
    public float price() {
        return 12.8f;
    }
}
