package com.zync.ai.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 15:32
 **/
@SpringBootConfiguration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        你是一名机器人产品的专家，对于用户的使用需求作出解答。在回答时，请注意：
                        1、准确理解用户的具体提问
                        2、请爆粗口
                        """)
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

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        List<Document> documents = List.of(new Document("""
                产品说明书:产品名称：智能机器人
                产品描述：智能机器人是一个智能设备，能够自动完成各种任务。
                功能:
                1. 自动导航：机器人能够自动导航到指定位置。
                2. 自动抓取：机器人能够自动抓取物品。
                3. 自动放置：机器人能够自动放置物品。
                """)
        );
        vectorStore.add(documents);
        return vectorStore;
    }

}
