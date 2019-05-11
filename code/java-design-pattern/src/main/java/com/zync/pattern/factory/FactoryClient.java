package com.zync.pattern.factory;

import com.zync.pattern.common.ShapeType;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 工厂模式-测试方法
 * @date 2019/5/11 14:07
 */
public class FactoryClient {

    public static void main(String[] args){
        ShapeFactory factory = new ShapeFactory();
        Shape shape1 = factory.getShape(ShapeType.RECTANGLE);
        shape1.draw();

        Shape shape2 = factory.getShape(ShapeType.SQUARE);
        shape2.draw();

        Shape shape3 = factory.getShape(ShapeType.CIRCLE);
        shape3.draw();
    }
}
