package com.zync.pattern.abstractfactory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 绿色
 * @date 2019/5/11 14:22
 */
public class Green implements Color {

    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}
