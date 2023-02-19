package com.zync.reactiveStream;

import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;

/**
 * 发布者
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/19 19:16
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
            System.out.println(Thread.currentThread().getName() + " publish " + item);
            publisher.submit(item);
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
