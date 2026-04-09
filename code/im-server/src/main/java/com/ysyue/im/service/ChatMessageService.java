package com.ysyue.im.service;

import com.ysyue.im.protocol.ImMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天消息服务
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 10:50
 **/
@Service
public class ChatMessageService {

    // 注入 MyBatis-Plus 的 Mapper
    // @Autowired private ImMessagePrivateMapper privateMapper;
    // @Autowired private ImMessageGroupMapper groupMapper;
    // @Autowired private ImGroupMemberMapper memberMapper;

    @Async("imTaskExecutor") // 指定自定义线程池，避免耗尽默认线程
    public void saveMessageAsync(ImMessage msg) {
        // ImMessagePrivate entity = new ImMessagePrivate();
        // entity.setSenderId(msg.getSenderId());
        // entity.setReceiverId(msg.getReceiverId());
        // entity.setContent(msg.getContent());
        // privateMapper.insert(entity);
        System.out.println("异步落库单聊消息完毕: " + msg.getMessageId());
    }

    @Async("imTaskExecutor")
    public void saveGroupMessageAsync(ImMessage msg) {
        // 群聊落库逻辑...
    }

    /**
     * 获取群成员列表 (建议结合 Redis List/Set 做缓存)
     */
    public List<String> getGroupMemberIds(String groupId) {
        // QueryWrapper<ImGroupMember> wrapper = new QueryWrapper<>();
        // wrapper.eq("group_id", groupId);
        // return memberMapper.selectList(wrapper).stream().map(ImGroupMember::getUserId).collect(Collectors.toList());
        return List.of("2", "3", "4"); // 模拟数据
    }

}
