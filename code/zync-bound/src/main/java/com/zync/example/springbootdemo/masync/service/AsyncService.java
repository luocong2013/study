package com.zync.example.springbootdemo.masync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 01396453(luocong)
 * @version 1.0.0
 * @description
 * @date 2020/8/3 16:09
 */
@Service
public class AsyncService {

    @Autowired
    private MAsyncService mAsyncService;

    public String async() {
        System.out.println("AsyncService: " + Thread.currentThread().getName());
        return mAsyncService.masync();
    }
}
