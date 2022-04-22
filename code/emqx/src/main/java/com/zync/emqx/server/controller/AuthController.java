package com.zync.emqx.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Emqx认证控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/16 15:56
 */
@RestController
@RequestMapping("/mqtt")
public class AuthController {

    private static Map<String, String> users = new HashMap<>(16);

    static {
        users.put("userhttp", "123");
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestParam("clientid") String clientid,
                                       @RequestParam("username") String username,
                                       @RequestParam("password") String password) {
        System.out.printf("EMQX开始认证，clientid=%s、username=%s、password=%s.\n", clientid, username, password);
        String value = users.get(username);
        if (!StringUtils.hasLength(value)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if (!value.equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(null);
    }

}
