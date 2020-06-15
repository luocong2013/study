package com.zync.classloader;

import java.lang.reflect.Method;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 16:29
 */
public class LoadClass {

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("com.zync.classloader.ClassLoaderTest", true, new CustomClassLoader());
            Object object = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("hello", String.class);
            Object val = method.invoke(object, "张三");
            System.out.println(val);


            Class<?> loadClazz = new CustomClassLoader().loadClass("com.zync.classloader.ClassLoaderTest");
            Object obj = loadClazz.newInstance();
            Method helloMethod = loadClazz.getDeclaredMethod("hello", String.class);
            Object value = helloMethod.invoke(obj, "李四");
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
