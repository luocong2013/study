package com.zync.pattern.factory;

import com.zync.pattern.common.ShapeType;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 图形工厂
 * @date 2019/5/11 13:57
 */
public class ShapeFactory {

    /**
     * 获取图形
     * @param shapeType
     * @return
     */
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
