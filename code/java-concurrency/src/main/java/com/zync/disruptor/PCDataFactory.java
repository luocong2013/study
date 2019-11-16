package com.zync.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption PCData对象的工厂类
 * @date 2019/11/16 21:07
 */
public class PCDataFactory implements EventFactory<PCData> {

    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
