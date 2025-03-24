package com.zync.ai.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 15:30
 **/
@RestController
@RequestMapping("/rag/chat-client")
public class RagSimpleChatClientController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagSimpleChatClientController(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
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
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .stream()
                .content();
    }

}
