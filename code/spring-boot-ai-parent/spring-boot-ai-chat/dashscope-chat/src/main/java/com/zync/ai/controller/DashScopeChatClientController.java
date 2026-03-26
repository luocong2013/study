package com.zync.ai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.chat.MessageFormat;
import com.alibaba.cloud.ai.dashscope.common.DashScopeApiConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
* @description 
* @author admin
* @version 1.0
* @since 2025/3/24 11:29
**/
@RestController
@RequestMapping("/dashscope/chat-client")
public class DashScopeChatClientController {

    private final ChatClient chatClient;

    public DashScopeChatClientController(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel)
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
     * 图片分析接口 - 通过 URL
     */
    @GetMapping("/image/analyze/url")
    public String analyzeImageByUrl(@RequestParam(defaultValue = "请分析这张图片的内容") String prompt,
                                    @RequestParam String imageUrl) {
        try {
            // 创建包含图片的用户消息
            List<Media> mediaList = List.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(imageUrl)));
            UserMessage message = UserMessage.builder()
                    .text(prompt)
                    .media(mediaList)
                    .build();

            // 设置消息格式为图片
            message.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);

            // 创建提示词，启用多模态模型
            Prompt chatPrompt = new Prompt(message,
                    DashScopeChatOptions.builder()
                            .model("qwen-image-max")  // 使用视觉模型
                            .multiModel(true)             // 启用多模态
                            .vlHighResolutionImages(true) // 启用高分辨率图片处理
                            .temperature(0.7)
                            .build());
            // 调用模型进行图片分析
            return chatClient.prompt(chatPrompt).call().content();
        } catch (Exception e) {
            return "图片分析失败: " + e.getMessage();
        }
    }

    /**
     * 图片分析接口 - 通过文件上传
     */
    @PostMapping("/image/analyze/upload")
    public String analyzeImageByUpload(@RequestParam(defaultValue = "请分析这张图片的内容") String prompt,
                                       @RequestParam("file") MultipartFile file) {
        try {
            // 验证文件类型
            if (!file.getContentType().startsWith("image/")) {
                return "请上传图片文件";
            }

            // 创建包含图片的用户消息
            Media media = new Media(MimeTypeUtils.parseMimeType(file.getContentType()), file.getResource());
            UserMessage message = UserMessage.builder()
                    .text(prompt)
                    .media(media)
                    .build();

            // 设置消息格式为图片
            message.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);

            // 创建提示词，启用多模态模型
            Prompt chatPrompt = new Prompt(message,
                    DashScopeChatOptions.builder()
                            .model("qwen-image-max")  // 使用视觉模型
                            .multiModel(true)             // 启用多模态
                            .vlHighResolutionImages(true) // 启用高分辨率图片处理
                            .temperature(0.7)
                            .build());

            // 调用模型进行图片分析
            return chatClient.prompt(chatPrompt).call().content();
        } catch (Exception e) {
            return "图片分析失败: " + e.getMessage();
        }
    }

}
