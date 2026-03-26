package com.zync.ai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/21 13:31
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final String DEFAULT_PROMPT = "你是一个博学的智能聊天助手，请根据用户提问回答！";

    private final ChatClient chatClient;

    public HelloController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem(DEFAULT_PROMPT)
                // 实现 Chat Memory 的 Advisor
                // 在使用 Chat Memory 时，需要指定对话ID，以便 Spring AI 处理上下文
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().maxMessages(100).build()).build()
                )
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                // .defaultOptions(
                //         DashScopeChatOptions.builder()
                //                 .topP(0.7)
                //                 .build()
                // )
                .build();
    }

    /**
     * ChatClient 简单调用
     * @param query
     * @return
     */
    @GetMapping("/simple/chat")
    public String simpleChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？") String query) {
        return chatClient
                .prompt(query)
                .call()
                .content();
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
        return chatClient
                .prompt(query)
                .stream()
                .content();
    }

    /**
     * ChatClient 使用自定义的 Advisor 实现功能增强.
     * eg:
     * http://127.0.0.1:18080/hello/advisor/chat/123?query=你好，我叫牧生，之后的会话中都带上我的名字
     * 好的，牧生。很高兴认识你！如果你有任何问题或需要帮助的地方，请随时告诉我，我会尽力为你解答。牧生，你想聊些什么呢？
     * http://127.0.0.1:18080/hello/advisor/chat/123?query=我叫什么名字？
     * 牧生，你叫牧生呀！如果有其他问题或需要帮助的地方，请随时告诉我哦！
     */
    @GetMapping("/advisor/chat/{id}")
    public Flux<String> advisorChat(@PathVariable("id") String id,
                                    @RequestParam("query") String query,
                                    HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return chatClient
                .prompt(query)
                .advisors(consumer -> consumer
                        .param(ChatMemory.CONVERSATION_ID, id)
                ).stream()
                .content();
    }

    /**
     * ChatClient 新的聊天接口，支持流式输出和自定义 ChatOptions 配置
     * eg:
     * http://127.0.0.1:18080/helloworld/advisor/newChat?query=你好&topP=0.8&temperature=0.9
     */
    @GetMapping("/advisor/newChat")
    public Flux<String> newChat(
            HttpServletResponse response,
            @RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？") String query,
            @RequestParam(value = "topP", required = false) Double topP,
            @RequestParam(value = "temperature", required = false) Double temperature,
            @RequestParam(value = "maxTokens", required = false) Integer maxToken) {

        response.setCharacterEncoding("UTF-8");

        // 构建 ChatOptions
        DashScopeChatOptions.DashScopeChatOptionsBuilder optionsBuilder = DashScopeChatOptions.builder();

        if (topP != null) {
            optionsBuilder.topP(topP);
        }
        if (temperature != null) {
            optionsBuilder.temperature(temperature);
        }
        if (maxToken != null) {
            optionsBuilder.maxToken(maxToken);
        }

        return this.chatClient.prompt(query)
                .options(optionsBuilder.build())
                .stream()
                .content();
    }

}
