package com.zync.ai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.zync.ai.tools.DateTimeTools;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 18:48
 **/
@RestController
@RequestMapping("/customize/tool")
public class CustomizeToolController {

    private final ChatClient chatClient;

    public CustomizeToolController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("你是一个博学的智能聊天助手，请根据用户提问回答！")
                // 实现 Chat Memory 的 Advisor
                // 在使用 Chat Memory 时，需要指定对话ID，以便 Spring AI 处理上下文
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withTopP(0.7)
                                .build()
                )
                .build();
    }

    /**
     * ChatClient 流式调用
     * @param query
     * @return
     */
    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(value = "query") String query,
                                   HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return chatClient
                .prompt(query)
                .tools(new DateTimeTools())
                .stream()
                .content();
    }

}
