package com.zync.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.nio.ByteBuffer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试
 * @date 2019/11/16 21:16
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        PCDataFactory factory = new PCDataFactory();
        int bufferSize = 1024;
        Disruptor<PCData> disruptor = new Disruptor<>(
                factory,
                bufferSize,
                new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build(),
                ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer());
        disruptor.start();
        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        for (long i = 0; true; i++) {
            buffer.putLong(0, i);
            producer.pushData(buffer);
            Thread.sleep(100);
            System.out.println("add data " + i);
        }
    }
}
