package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 汉堡
 * @date 2019/5/11 15:09
 */
public abstract class AbstractBurger implements Item {

    @Override
    public Packing packing() {
        return new Wrapper();
    }
}
