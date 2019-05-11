package com.zync.pattern.abstractfactory;

import com.zync.pattern.common.ColorType;
import com.zync.pattern.common.ShapeType;
import com.zync.pattern.factory.Shape;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 抽象工厂
 * @date 2019/5/11 14:23
 */
public abstract class AbstractFactory {

    /**
     * 获取颜色
     * @param colorType
     * @return
     */
    public abstract Color getColor(ColorType colorType);

    /**
     * 获取图形
     * @param shapeType
     * @return
     */
    public abstract Shape getShape(ShapeType shapeType);
}
