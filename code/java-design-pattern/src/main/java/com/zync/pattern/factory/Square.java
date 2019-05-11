package com.zync.pattern.factory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 正方形
 * @date 2019/5/11 13:56
 */
public class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
