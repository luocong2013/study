package com.zync.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 消费者实现 WorkHandler接口
 * @date 2019/11/16 21:04
 */
public class Consumer implements WorkHandler<PCData> {

    @Override
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event: --" + event.getValue() * event.getValue() + "--");
    }
}
