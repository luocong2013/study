package com.zync.example.springbootdemo.masync.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 01396453(luocong)
 * @version 1.0.0
 * @description 方法异步执行服务层
 * @date 2020/8/3 16:00
 */
@Service
public class MAsyncService {

    @Async
    public String masync() {
        System.out.println("MAsyncService: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "MAsyncService";
    }
}
