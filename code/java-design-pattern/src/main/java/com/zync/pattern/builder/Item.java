package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 食物条目接口
 * @date 2019/5/11 15:03
 */
public interface Item {
    /**
     * 名字
     * @return
     */
    String name();

    /**
     * 食物包装方式
     * @return
     */
    Packing packing();

    /**
     * 价格
     * @return
     */
    float price();
}
