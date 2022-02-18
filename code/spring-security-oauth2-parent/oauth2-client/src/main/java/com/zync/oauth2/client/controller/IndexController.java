package com.zync.oauth2.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Index 控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/15 16:20
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public Map<String, String> index() {
        return Collections.singletonMap("hello", "OAuth2.0");
    }
}
