package com.zync.map;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 跳表
 * @date 2019/10/28 22:21
 */
public class SkipListDemo {

    public static void main(String[] args){
        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
        IntStream.range(0, 30).forEach(item -> map.put(item, item));
        map.forEach((k, v) -> System.out.println(k));
    }
}
