package com.zync.websocket.springboot.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * web socket 配置类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 14:47
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册WebSocket处理器，路径为/ws/{userId}，允许跨域
        registry.addHandler(new MyWebSocketHandler(), "/ws")
                .addInterceptors(new UserIdHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}
