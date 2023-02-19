package com.zync.reactiveStream;


import java.util.concurrent.Flow;

/**
 * 订阅者
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/19 19:17
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
