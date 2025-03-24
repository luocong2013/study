package com.zync.ai.client;

import com.zync.ai.service.CustomerSupportAssistant;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 16:51
 **/
@RestController
@RequestMapping("/ai/agent")
public class AiAgentController {

    private final CustomerSupportAssistant agent;

    public AiAgentController(CustomerSupportAssistant agent) {
        this.agent = agent;
    }

    @RequestMapping("/chat")
    public Flux<String> chat(@RequestParam("chatId") String chatId,
                             @RequestParam("userMessage") String userMessage,
                             HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return agent.chat(chatId, userMessage);
    }

}
