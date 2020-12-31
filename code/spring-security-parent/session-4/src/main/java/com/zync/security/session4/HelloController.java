package com.zync.security.session4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author luocong
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/set")
    public String set(HttpSession session) {
        session.setAttribute("user", "大力水手");
        return String.valueOf(port);
    }

    @GetMapping("/get")
    public String get(HttpSession session) {
        return session.getAttribute("user") + ":" + port;
    }
}