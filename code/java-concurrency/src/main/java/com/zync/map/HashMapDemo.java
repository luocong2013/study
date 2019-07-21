package com.zync.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption HashMap测试
 * @date 2019/7/21 15:11
 */
public class HashMapDemo {

    public static void main(String[] args){
        // put
        Map<Integer, String> map = new HashMap<>(16);
        map.put(1, "张三");
        map.put(2, "李四");
        String value = map.put(1, "王五");
        System.out.println("put替换的值：" + value);
        map.forEach((k, v) -> System.out.println("key: " + k + ", value: " + v));

        // putIfAbsent
        Map<Integer, String> putIfAbsentMap = new HashMap<>(16);
        putIfAbsentMap.putIfAbsent(1, "张三1");
        putIfAbsentMap.putIfAbsent(2, "李四1");
        String value1 = putIfAbsentMap.putIfAbsent(1, "王五1");
        System.out.println("putIfAbsent替换的值：" + value1);
        putIfAbsentMap.forEach((k, v) -> System.out.println("key: " + k + ", value: " + v));

        // 输出结果：
        // put替换的值：张三
        // key: 1, value: 王五
        // key: 2, value: 李四
        // putIfAbsent替换的值：张三1
        // key: 1, value: 张三1
        // key: 2, value: 李四1
    }
}
