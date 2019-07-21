package com.zync.ref;

import com.zync.common.User;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 虚引用，随时会被回收， 创建了可能很快就会被回收
 *             业界暂无使用场景， 可能被JVM团队内部用来跟踪JVM的垃圾回收活动
 * @date 2019/7/21 14:33
 */
public class PhantomReferenceDemo {

    public static void main(String[] args){
        PhantomReference<User> phantomReference = new PhantomReference<>(
                new User("张三", "123", 30),
                new ReferenceQueue<>());
        // 运行后，发现结果总是null，引用跟没有持有差不多
        System.out.println(phantomReference.get());
    }
}
