package com.zync.pattern.abstractfactory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 红色
 * @date 2019/5/11 14:21
 */
public class Red implements Color {

    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}
