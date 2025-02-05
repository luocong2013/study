package com.zync.thre;

/**
 * @author admin
 * @version 1.0
 * @description 父线程用 InheritableThreadLocal 给子线程传值
 * @since 2025/2/5 15:14
 **/
public class InheritableThreadLocalDemo {

    public static void main(String[] args) {
        InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("父线程的值");

        new Thread(() -> {
            System.out.println("子线程获取的值：" + threadLocal.get());
        }).start();
    }
}
