package com.zync.lock;

import java.util.Vector;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 锁消除
 *      逃逸分析的JVM参数（Java SE 6u23+开始支持，并默认启用）：
 *      1. 开启逃逸分析：-XX:+DoEscapeAnalysis
 *      2. 关闭逃逸分析：-XX:-DoEscapeAnalysis
 *      3. 显示分析结果：-XX:+PrintEscapeAnalysis
 *
 *      锁消除的JVM参数：
 *      1. 开启锁消除（jdk1.8中默认开启）：-XX:+EliminateLocks
 *      2. 关闭锁消除：-XX:-EliminateLocks
 * @date 2019/11/12 21:58
 */
public class RemoveLock {

    private static StringBuffer buffer = new StringBuffer();


    public static void main(String[] args){
        RemoveLock lock = new RemoveLock();
        buffer.append(lock.createString());
        buffer.append("aaaa");
        System.out.println(buffer.toString());
    }

    public String[] createStrings() {
        Vector<String> v = new Vector<>();
        for (int i = 0; i < 100; i++) {
            v.add(Integer.toString(i));
        }
        return v.toArray(new String[]{});
    }

    public StringBuffer createString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            buffer.append(i);
        }
        return buffer;
    }
}
