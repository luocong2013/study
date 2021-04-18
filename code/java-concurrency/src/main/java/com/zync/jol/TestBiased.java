package com.zync.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试打印对象头信息
 * @date 2021/4/18 14:37
 */
public class TestBiased {

    public static void main(String[] args){
        Dog dog = new Dog();
        // 调用hashCode之后，会禁用偏向锁，因为调用了对象的hashCode方法，会在Mark Word中生成hashCode，占32位，而要使用偏向锁必须要用54位来存线程ID，所以没地存了（对象头Mark Word中共64位），只能禁用偏向锁了
        // 偏向锁默认是开启的
        // 01 -> 无锁  00 -> 轻量级锁   10 -> 重量级锁   11 -> Marked for GC
        // 101 -> 偏向锁  001 -> 非偏向锁
        // synchronized 锁升级顺序：偏向锁 -> 轻量级锁 -> 重量级锁（有自旋优化）
        //dog.hashCode();
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());

        synchronized (dog) {
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(dog).toPrintable());
    }
}
