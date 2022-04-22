package com.zync.emqx.server.controller;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/mqtt")
public class AuthController {

    private static Map<String, String> users = new HashMap<>(16);

    static {
        users.put("userhttp", "123");
        users.put("emq-client2", "123456");
        users.put("emq-client3", "123456");
        users.put("admin", "admin");
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestParam("clientid") String clientid,
                                       @RequestParam("username") String username,
                                       @RequestParam("password") String password) {
        log.info("emqx http认证组件开始调用任务服务完成认证,clientid={},username={},password={}", clientid, username, password);
        String value = users.get(username);
        if (!StringUtils.hasLength(value)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if (!value.equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/superuser")
    public ResponseEntity<Object> superuser(@RequestParam("clientid") String clientid,
                                            @RequestParam("username") String username) {

        log.info("emqx 查询是否是超级用户,clientid={},username={}", clientid, username);

        if (clientid.contains("admin") || username.contains("admin")) {
            log.info("用户{}是超级用户", username);
            return ResponseEntity.ok(null);
        }
        log.info("用户{}不是超级用户", username);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/acl")
    public ResponseEntity<Object> acl(@RequestParam("access") int access,
                              @RequestParam("username") String username,
                              @RequestParam("clientid") String clientid,
                              @RequestParam("ipaddr") String ipaddr,
                              @RequestParam("topic") String topic,
                              @RequestParam("mountpoint") String mountpoint) {
        log.info("EMQX发起客户端操作授权查询请求,access={},username={},clientid={},ipaddr={},topic={},mountpoint={}",
                access, username, clientid, ipaddr, topic, mountpoint);

        if (username.equals("emq-client2") && topic.equals("test/#") && access == 1) {
            log.info("客户端{}有权限订阅{}", username, topic);
            return ResponseEntity.ok(null);
        }
        if (username.equals("emq-client3") && topic.equals("test/123") && access == 2) {
            log.info("客户端{}有权限向{}发布消息", username, topic);
            return ResponseEntity.ok(null);
        }
        log.info("客户端{},username={},没有权限对主题{}进行{}操作", clientid, username, topic, access == 1 ? "订阅" : "发布");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}
