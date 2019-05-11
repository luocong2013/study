package com.zync.pattern.abstractfactory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 蓝色
 * @date 2019/5/11 14:23
 */
public class Blue implements Color {

    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}
