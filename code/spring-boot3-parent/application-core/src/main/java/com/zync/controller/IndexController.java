package com.zync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author admin
 * @version 1.0
 * @description 进入首页
 * @since 2025/4/1 18:38
 **/
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/stream")
    public String streamIndex() {
        return "stream-index";
    }

}
