package com.zync.pattern.abstractfactory;

import com.zync.pattern.common.FactoryType;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 工厂创造器
 * @date 2019/5/11 14:28
 */
public class FactoryProducer {

    /**
     * 获取工厂
     * @param factoryType
     * @return
     */
    public static AbstractFactory getFactory(FactoryType factoryType) {
        if (factoryType == null) {
            return null;
        }
        if (FactoryType.SHAPE.equals(factoryType)) {
            return new ShapeFactory();
        } else if (FactoryType.COLOR.equals(factoryType)) {
            return new ColorFactory();
        }
        return null;
    }
}
