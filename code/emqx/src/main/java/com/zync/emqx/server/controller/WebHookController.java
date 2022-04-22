package com.zync.emqx.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * WebHook控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/22 22:43
 */
@Slf4j
@RestController
@RequestMapping("/mqtt")
public class WebHookController {

    private final Map<String, Boolean> clientStatus = new HashMap<>();

    @PostMapping("/webhook")
    public void hook(@RequestBody Map<String, Object> params) {
        log.info("emqx 触发 webhook,请求体数据={}", params);

        String action = (String) params.get("action");
        String clientId = (String) params.get("clientid");
        if (action.equals("client_connected")) {
            log.info("客户端{}接入本系统", clientId);
            clientStatus.put(clientId, true);
        }

        if (action.equals("client_disconnected")) {
            log.info("客户端{}下线", clientId);
            clientStatus.put(clientId, false);
        }
    }

    @GetMapping("/allStatus")
    public Map<String, Boolean> getStatus() {
        return this.clientStatus;
    }

}
