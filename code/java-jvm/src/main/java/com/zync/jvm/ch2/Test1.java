package com.zync.jvm.ch2;

/**
 * 虚拟机栈-局部变量表
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/25 19:03
 */
public class Test1 {

    public void test1(int k, int m) {
        {
            int a = 1;
            int b = 2;
        }
        {
            int c = 1;
        }
        int i = 0;;
        long j = 1;
    }
}
