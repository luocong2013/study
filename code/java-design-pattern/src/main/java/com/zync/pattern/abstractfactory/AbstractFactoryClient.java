package com.zync.pattern.abstractfactory;

import com.zync.pattern.common.ColorType;
import com.zync.pattern.common.FactoryType;
import com.zync.pattern.common.ShapeType;
import com.zync.pattern.factory.Shape;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 抽象工厂模式-测试方法
 * @date 2019/5/11 14:33
 */
public class AbstractFactoryClient {

    public static void main(String[] args){
        // 获取图形工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory(FactoryType.SHAPE);
        Shape shape1 = shapeFactory.getShape(ShapeType.RECTANGLE);
        shape1.draw();
        Shape shape2 = shapeFactory.getShape(ShapeType.SQUARE);
        shape2.draw();
        Shape shape3 = shapeFactory.getShape(ShapeType.CIRCLE);
        shape3.draw();

        // 获取颜色工厂
        AbstractFactory colorFactory = FactoryProducer.getFactory(FactoryType.COLOR);
        Color color1 = colorFactory.getColor(ColorType.RED);
        color1.fill();
        Color color2 = colorFactory.getColor(ColorType.GREEN);
        color2.fill();
        Color color3 = colorFactory.getColor(ColorType.BLUE);
        color3.fill();
    }
}
