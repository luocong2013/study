package com.zync.service;

import org.springframework.stereotype.Service;

/**
 * Demo Service
 *
 * @author luocong
 * @version v1.0
 * @date 2022/12/12 20:16
 */
@Service
public class DemoService {

    public String hi(String name) {
        return "Hi! " + name;
    }

}
