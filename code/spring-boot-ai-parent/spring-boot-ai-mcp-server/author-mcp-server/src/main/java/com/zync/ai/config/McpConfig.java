package com.zync.ai.config;

import com.zync.ai.repository.AuthorRepository;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/26 19:43
 **/
@SpringBootConfiguration
public class McpConfig {

    @Bean
    public ToolCallbackProvider authorTools(AuthorRepository authorRepository) {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(authorRepository)
                .build();
    }

}
