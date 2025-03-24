package com.zync.ai.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
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
 * @since 2025/3/24 13:50
 **/
@RestController
@RequestMapping("/ollama/chat-model")
public class OllamaChatModelController {

    private final ChatModel chatModel;

    public OllamaChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * ChatClient 简单调用
     * @param query
     * @return
     */
    @GetMapping("/simple/chat")
    public String simpleChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？") String query) {
        return chatModel.call(query);
    }

    /**
     * ChatClient 流式调用
     * @param query
     * @return
     */
    @GetMapping("/stream/chat")
    public Flux<String> streamChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？") String query,
                                   HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return chatModel.stream(query);
    }

    /**
     * 使用编程方式自定义 LLMs ChatOptions 参数， {@link org.springframework.ai.ollama.api.OllamaOptions}
     * 优先级高于在 application.yml 中配置的 LLMs 参数！
     */
    @GetMapping("/custom/chat")
    public String customChat(@RequestParam("query") String query) {

        OllamaOptions options = OllamaOptions.builder()
                .topP(0.7)
                .model("deepseek-r1:7b")
                .temperature(0.8)
                .build();

        return chatModel.call(new Prompt(query, options)).getResult().getOutput().getText();
    }
}
