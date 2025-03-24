package com.zync.ai.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 14:03
 **/
@RestController
@RequestMapping("/qwq/chat-client")
public class QWQChatClientController {

    private final ChatClient chatClient;

    public QWQChatClientController(ChatClient.Builder builder) {
        // this.chatClient = builder
        //         // 实现 Chat Memory 的 Advisor
        //         // 在使用 Chat Memory 时，需要指定对话ID，以便 Spring AI 处理上下文
        //         .defaultAdvisors(
        //                 new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
        //
        //                 // 整合 QWQ 的思考过程到输出中
        //                 new ReasoningContentAdvisor(0)
        //         )
        //         // 实现 Logger 的 Advisor
        //         .defaultAdvisors(
        //                 new SimpleLoggerAdvisor()
        //         )
        //         // 设置 ChatClient 中 ChatModel 的 Options 参数
        //         .defaultOptions(
        //                 DashScopeChatOptions.builder()
        //                         .withTopP(0.7)
        //                         .build()
        //         )
        //         .build();

        // 也可全局配置: @link com.zync.ai.customizer.CustomChatClientCustomizer

        this.chatClient = builder.build();
    }

    /**
     * ChatClient 简单调用
     *
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
     *
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
                        .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, id)
                        .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                ).stream()
                .content();
    }

}
