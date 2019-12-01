package com.zync.pubsub;

import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 发布者
 * @date 2019/12/1 21:41
 */
public class MyPublisher {

    public static void main(String[] args){
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        MySubscriber<String> subscriber = new MySubscriber<>();
        MySubscriber<String> subscriber2 = new MySubscriber<>();

        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber2);

        System.out.println("Publishing data items ...");
        String[] items = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Arrays.stream(items).forEach(item -> {
            publisher.submit(item);
            System.out.println(Thread.currentThread().getName() + " publish " + item);
        });
        publisher.close();

        try {
            synchronized ("A") {
                "A".wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
