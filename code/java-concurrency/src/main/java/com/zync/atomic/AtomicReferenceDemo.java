package com.zync.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption AtomicReference测试，无锁的对象引用
 * @date 2019/11/13 22:44
 */
public class AtomicReferenceDemo {

    private static AtomicReference<Integer> money = new AtomicReference<>();
    static {
        money.set(19);
    }

    public static void main(String[] args){
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    while (true) {
                        final Integer m = money.get();
                        if (m < 20) {
                            if (money.compareAndSet(m, m + 20)) {
                                System.out.println("余额小于20元，充值成功，余额：" + money.get() + "元");
                                break;
                            }
                        } else {
                            //System.out.println("余额大于20元，无需充值");
                            break;
                        }
                    }
                }
            }).start();
        }

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    final Integer m = money.get();
                    if (m > 10) {
                        System.out.println("大于10元");
                        if (money.compareAndSet(m, m - 10)) {
                            System.out.println("成功消费10元，余额：" + money.get());
                            break;
                        }
                    } else {
                        System.out.println("没有足够的余额");
                        break;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
