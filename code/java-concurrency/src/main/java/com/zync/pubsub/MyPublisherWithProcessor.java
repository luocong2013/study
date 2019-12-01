package com.zync.pubsub;

import java.util.concurrent.SubmissionPublisher;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 针对数据流的处理链条
 * @date 2019/12/1 22:07
 */
public class MyPublisherWithProcessor {

    public static void main(String[] args){
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        MySubscriber<String> subscriber = new MySubscriber<>();
        MySubscriber<String> subscriber2 = new MySubscriber<>();

        TransformProcessor<String, String> toUpperCase = new TransformProcessor<>(String::toUpperCase);
        TransformProcessor<String, String> toLowerCase = new TransformProcessor<>(String::toLowerCase);

        publisher.subscribe(toUpperCase);
        publisher.subscribe(toLowerCase);

        toUpperCase.subscribe(subscriber);
        toLowerCase.subscribe(subscriber2);
    }
}
