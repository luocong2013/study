package com.zync.pattern.abstractfactory;

import com.zync.pattern.common.ColorType;
import com.zync.pattern.common.ShapeType;
import com.zync.pattern.factory.Shape;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 颜色工厂
 * @date 2019/5/11 14:27
 */
public class ColorFactory extends AbstractFactory {

    @Override
    public Color getColor(ColorType colorType) {
        if (colorType == null) {
            return null;
        }
        if (ColorType.RED.equals(colorType)) {
            return new Red();
        } else if (ColorType.GREEN.equals(colorType)) {
            return new Green();
        } else if (ColorType.BLUE.equals(colorType)) {
            return new Blue();
        }
        return null;
    }

    @Override
    public Shape getShape(ShapeType shapeType) {
        return null;
    }
}
