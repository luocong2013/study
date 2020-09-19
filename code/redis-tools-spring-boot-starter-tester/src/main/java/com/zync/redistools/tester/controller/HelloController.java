package com.zync.redistools.tester.controller;

import com.zync.boot.redistools.annotation.DistributedLock;
import com.zync.boot.redistools.common.DistributedLockValueType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试控制器
 * @date 2020/9/19 18:19
 */
@RestController("/hello")
public class HelloController {

    /**
     * 测试值类型为UUID
     * @param name
     * @return
     */
    @DistributedLock(name = "hello", valueType = DistributedLockValueType.UUID, timeout = 100)
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name;
    }

    /**
     * 测试值类型为雪花算法生成的分布式ID
     * @param name
     * @return
     */
    @DistributedLock(name = "hello2")
    @GetMapping("/hello2/{name}")
    public String hello2(@PathVariable String name) {
        return "hello2 " + name;
    }

    /**
     * 测试自定义值
     * @param name
     * @return
     */
    @DistributedLock(name = "hello3", value = "自定义值", valueType = DistributedLockValueType.CUSTOMER, timeout = 100)
    @GetMapping("/hello3/{name}")
    public String hello3(@PathVariable String name) {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello3 " + name;
    }
}
