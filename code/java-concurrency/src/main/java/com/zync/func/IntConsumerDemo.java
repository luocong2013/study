package com.zync.func;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 * @date 2019/11/26 22:42
 */
public class IntConsumerDemo {

    private static int[] arr = {1, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args){
        IntConsumer outprintln = System.out::println;
        IntConsumer errprintln = System.err::println;
        Arrays.stream(arr).forEach(outprintln.andThen(errprintln));
    }
}
