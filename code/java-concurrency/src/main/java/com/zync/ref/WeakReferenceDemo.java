package com.zync.ref;

import com.zync.common.User;

import java.lang.ref.WeakReference;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 弱引用，只能存活到下一次垃圾收集之前
 *             生命周期很短的对象，例如ThreadLocal中的Key
 * @date 2019/7/21 14:22
 */
public class WeakReferenceDemo {

    public static void main(String[] args){
        WeakReference<User> weakReference = new WeakReference<>(new User("张三", "123", 11));
        System.out.println("GC之前，弱引用的值：" + weakReference.get());
        System.gc();
        System.out.println("GC之后，弱引用的值：" + weakReference.get());
    }
}
