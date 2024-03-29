package com.zync.chat.server.session;

import lombok.Data;

import java.util.Collections;
import java.util.Set;

/**
 * 聊天组，即聊天室 数据对象
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:15
 */
@Data
public class Group {
    /**
     * 聊天室名称
     */
    private String name;
    /**
     * 聊天室成员
     */
    private Set<String> members;

    public static final Group EMPTY_GROUP = new Group("empty", Collections.emptySet());

    public Group(String name, Set<String> members) {
        this.name = name;
        this.members = members;
    }
}
