package com.zync.pattern.factory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 圆
 * @date 2019/5/11 13:57
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
