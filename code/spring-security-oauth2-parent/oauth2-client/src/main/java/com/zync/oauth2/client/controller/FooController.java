package com.zync.oauth2.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Foo 控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/15 16:23
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping("/bar")
    public Map<String, Object> bar(@RegisteredOAuth2AuthorizedClient("lalita") OAuth2AuthorizedClient client) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>(4);
        map.put("authentication", authentication);
        map.put("oAuth2AuthorizedClient", client);
        return map;
    }

}
