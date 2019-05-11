package com.zync.pattern.abstractfactory;

import com.zync.pattern.common.ColorType;
import com.zync.pattern.common.ShapeType;
import com.zync.pattern.factory.Circle;
import com.zync.pattern.factory.Rectangle;
import com.zync.pattern.factory.Shape;
import com.zync.pattern.factory.Square;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 图形工厂
 * @date 2019/5/11 14:25
 */
public class ShapeFactory extends AbstractFactory {

    @Override
    public Color getColor(ColorType colorType) {
        return null;
    }

    @Override
    public Shape getShape(ShapeType shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (ShapeType.RECTANGLE.equals(shapeType)) {
            return new Rectangle();
        } else if (ShapeType.SQUARE.equals(shapeType)) {
            return new Square();
        } else if (ShapeType.CIRCLE.equals(shapeType)) {
            return new Circle();
        }
        return null;
    }
}
