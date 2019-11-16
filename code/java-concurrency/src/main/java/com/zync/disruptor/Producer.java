package com.zync.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 生产者
 * @date 2019/11/16 21:08
 */
public class Producer {

    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer buffer) {
        long sequence = ringBuffer.next();
        try {
            PCData event = ringBuffer.get(sequence);
            event.setValue(buffer.getLong(0));
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
