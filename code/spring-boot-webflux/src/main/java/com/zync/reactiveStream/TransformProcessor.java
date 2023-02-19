package com.zync.reactiveStream;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

/**
 * 数据处理链
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/19 19:25
 */
public class TransformProcessor<T, R> extends SubmissionPublisher<R> implements Flow.Processor<T, R> {

    private final Function<? super T, ? extends R> function;
    private Flow.Subscription subscription;

    public TransformProcessor(Function<? super T, ? extends R> function) {
        super();
        this.function = function;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        submit(function.apply(item));
        // 背压
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        close();
    }
}
