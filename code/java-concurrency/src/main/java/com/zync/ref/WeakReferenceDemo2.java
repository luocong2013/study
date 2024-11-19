package com.zync.ref;

import com.zync.common.User;

import java.lang.ref.WeakReference;

/**
 * 弱引用在GC之后不被回收的情况
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/30 13:04
 */
public class WeakReferenceDemo2 {

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }

    /**
     * GC之后弱引用不会被垃圾回收
     * 因为被弱引用持有的对象 是强引用
     * 局部变量可作为 GC Roots 对象
     */
    private static void test1() {
        User user = new User("张三", "123", 11);

        WeakReference<User> weakReference = new WeakReference<>(user);
        System.out.println("GC之前，弱引用的值：" + weakReference.get());
        System.gc();
        System.out.println("GC之后，弱引用的值：" + weakReference.get());
    }

    /**
     * 同 test1
     */
    private static void test2() {
        WeakReference<User> weakReference = new WeakReference<>(new User("张三", "123", 11));
        User user = weakReference.get();
        System.out.println("GC之前，弱引用的值：" + weakReference.get());
        System.gc();
        System.out.println("GC之后，弱引用的值：" + weakReference.get());
    }

    /**
     * 同 test1
     * ThreadLocalMap.Entry 里 key是弱引用, 指向的是 ThreadLocal 对象
     * 这里 ThreadLocal 对象是强引用、局部变量 可作为 GC Roots 对象
     */
    private static void test3() {
        ThreadLocal<User> local = ThreadLocal.withInitial(User::new);
        local.set(new User("张三", "123", 11));
        System.out.println("GC之前，弱引用的值：" + local.get());
        System.gc();
        System.out.println("GC之后，弱引用的值：" + local.get());
    }


    /**
     * 我们一般这样使用，比如 Spring Security 里保存用户认证信息
     * 同 test3
     * 常量引用的对象 可作为 GC Roots 对象
     */
    private static final ThreadLocal<User> THREAD_LOCAL = ThreadLocal.withInitial(User::new);
    private static void test4() {
        THREAD_LOCAL.set(new User("张三", "123", 11));
        System.out.println("GC之前，弱引用的值：" + THREAD_LOCAL.get());
        System.gc();
        System.out.println("GC之后，弱引用的值：" + THREAD_LOCAL.get());
    }

    /**
     * 这种就属于典型的内存泄漏代码
     * 获取到的 ThreadLocal 没有强引用，那么 ThreadLocal 将在下一次gc时被回收
     * 导致 ThreadLocalMap.Entry 里 key 被回收，value 还在，但是不能被访问到（内存泄漏）
     * 强引用链：Thread -> ThreadLocalMap -> Entry -> value
     */
    private static void test5() {
        getThreadLocal();
        Thread thread = Thread.currentThread();
        System.gc();
        thread = Thread.currentThread();
    }

    private static ThreadLocal<User> getThreadLocal() {
        ThreadLocal<User> local = ThreadLocal.withInitial(User::new);
        local.set(new User("张三", "123", 11));
        return local;
    }


}
