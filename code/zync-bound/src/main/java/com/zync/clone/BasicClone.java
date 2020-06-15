package com.zync.clone;

/**
 * @author luocong
 * @description 基本类型clone
 * @date 2020/5/25 14:21
 */
public class BasicClone {

    public static void main(String[] args) {
        int num1=2;
        int num2=num1;
        num2=4;
        System.out.println(num1==num2);


        int[] a={2,1,4,3,5};
        int[] b=a;
        b[0]=8;
        System.out.println(a==b);
        System.out.println(a[0]);

        int[] c={2,1,4,3,5};
        int[] d=c.clone();
        d[0]=8;
        System.out.println(c==d);
        System.out.println(c[0]);
    }
}
