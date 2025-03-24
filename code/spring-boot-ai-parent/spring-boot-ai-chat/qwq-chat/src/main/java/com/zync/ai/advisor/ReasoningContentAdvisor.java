package com.zync.ai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 14:04
 **/
public class ReasoningContentAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(ReasoningContentAdvisor.class);

    private final int order;

    public ReasoningContentAdvisor(int order) {
        this.order = order;
    }

    @Override
    public AdvisedRequest before(AdvisedRequest request) {
        return request;
    }

    @Override
    public AdvisedResponse after(AdvisedResponse advisedResponse) {

        ChatResponse response = advisedResponse.response();
        if (response == null) {
            return advisedResponse;
        }

        logger.debug(String.valueOf(response.getResults().get(0).getOutput().getMetadata()));

        String reasoningContent = String.valueOf(response.getResults().get(0).getOutput().getMetadata().get("reasoningContent"));

        if (StringUtils.hasText(reasoningContent)) {
            List<Generation> thinkGenerations = response.getResults().stream()
                    .map(generation -> {
                        AssistantMessage output = generation.getOutput();
                        AssistantMessage thinkAssistantMessage = new AssistantMessage(
                                String.format("<think>%s</think>", reasoningContent) + output.getText(),
                                output.getMetadata(),
                                output.getToolCalls(),
                                output.getMedia()
                        );
                        return new Generation(thinkAssistantMessage, generation.getMetadata());
                    }).toList();

            ChatResponse thinkChatResp = ChatResponse.builder().from(response).generations(thinkGenerations).build();
            return AdvisedResponse.from(advisedResponse).response(thinkChatResp).build();

        }

        return advisedResponse;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
