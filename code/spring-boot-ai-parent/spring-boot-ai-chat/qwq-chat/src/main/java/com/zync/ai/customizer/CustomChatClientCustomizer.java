package com.zync.ai.customizer;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.zync.ai.advisor.ReasoningContentAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientCustomizer;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 14:22
 **/
@Component
public class CustomChatClientCustomizer implements ChatClientCustomizer {

    @Override
    public void customize(ChatClient.Builder builder) {
        builder
                // 实现 Chat Memory 的 Advisor
                // 在使用 Chat Memory 时，需要指定对话ID，以便 Spring AI 处理上下文
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()),

                        // 整合 QWQ 的思考过程到输出中
                        new ReasoningContentAdvisor(0)
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
                );
    }
}
