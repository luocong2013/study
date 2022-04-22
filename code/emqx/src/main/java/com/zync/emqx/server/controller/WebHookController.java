package com.zync.emqx.server.controller;

import com.zync.emqx.mqtt.enums.QosEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
            // 自动为客户端产生订阅
            autoSub(clientId, "autoSub/#", QosEnum.QoS2, true);
        }

        if (action.equals("client_disconnected")) {
            log.info("客户端{}下线", clientId);
            clientStatus.put(clientId, false);
            // 自动的为客户端取消订阅
            autoSub(clientId, "autoSub/#", QosEnum.QoS2, false);
        }
    }

    @GetMapping("/allStatus")
    public Map<String, Boolean> getStatus() {
        return this.clientStatus;
    }

    /**
     * 自动的订阅和取消订阅
     *
     * @param clientId
     * @param topicFilter
     * @param qos
     * @param sub
     */
    private void autoSub(String clientId, String topicFilter, QosEnum qos, boolean sub) {
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin", "public")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        //封装请求参数
        Map<String, Object> params = new HashMap<>(8);
        params.put("clientid", clientId);
        params.put("topic", topicFilter);
        params.put("qos", qos.value());

        //设置头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //设置请求体
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);

        //发送请求
        if (sub) {
            //自动订阅
            new Thread(() -> {
                ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://127.0.0.1:8081/api/v4/mqtt/subscribe", entity, String.class);
                log.info("自动订阅的结果:{}", responseEntity.getBody());
            }).start();
            return;
        }
        //取消订阅
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://127.0.0.1:8081/api/v4/mqtt/unsubscribe", entity, String.class);
        log.info("自动取消订阅的结果:{}", responseEntity.getBody());
    }

}
