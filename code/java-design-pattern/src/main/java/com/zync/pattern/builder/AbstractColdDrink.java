package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 冷饮
 * @date 2019/5/11 15:11
 */
public abstract class AbstractColdDrink implements Item {

    @Override
    public Packing packing() {
        return new Bottle();
    }
}
