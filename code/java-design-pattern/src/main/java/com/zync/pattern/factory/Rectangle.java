package com.zync.pattern.factory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 长方形
 * @date 2019/5/11 13:54
 */
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
