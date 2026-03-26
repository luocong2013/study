package com.zync.ai.config;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 15:32
 **/
@SpringBootConfiguration
public class ChatConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    public CommandLineRunner ingestTermOfServiceToVectorStore(VectorStore vectorStore,
                                                              @Value("classpath:rag/terms-of-service.txt") Resource resource) {
        return args -> {
            vectorStore.write(new TokenTextSplitter().transform(new TextReader(resource).read()));

            vectorStore.similaritySearch("Cancelling Bookings").forEach(doc -> System.out.println("Similar Document: " + doc.getText()));
        };
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(100).build();
    }

}
