package com.zync.pubsub;

import java.util.concurrent.Flow;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 订阅者
 * @date 2019/12/1 21:21
 */
public class MySubscriber<T> implements Flow.Subscriber<T> {

    private Flow.Subscription subscription;

    /**
     * 在注册后首先被调用
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        // 请求一个数据流中的数据
        subscription.request(1);
        System.out.println(Thread.currentThread().getName() + " onSubscribe");
    }

    @Override
    public void onNext(T item) {
        System.out.println(Thread.currentThread().getName() + " Received: " + item);
        // 再次请求剩余的数据
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        synchronized ("A") {
            "A".notifyAll();
        }
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
        synchronized ("A") {
            "A".notifyAll();
        }
    }
}
