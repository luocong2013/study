-- ====================================================================================================
-- 聊天相关表结构
-- ====================================================================================================
-- 群组表
CREATE TABLE `t_im_group` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT 'ID，自增主键',
    `name`                  varchar(100)          NOT NULL COMMENT '群名称',
    `avatar`                varchar(255)                   DEFAULT NULL COMMENT '群头像URL',
    `member_count`          int(11)                        DEFAULT NULL COMMENT '成员数量',
    `max_members`           int(11)                        DEFAULT NULL COMMENT '最大成员数量',
    `description`           text                           DEFAULT NULL COMMENT '群聊描述',
    `related_id`            bigint(20)                     DEFAULT NULL COMMENT '关联ID（如活动ID等）',
    `creator_id`            bigint(20) unsigned   NOT NULL COMMENT '创建人ID',
    `creator_name`          varchar(20)                    DEFAULT NULL COMMENT '创建人姓名',
    `created_at`            datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`            datetime                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`               tinyint(4)            NOT NULL DEFAULT 0 COMMENT '是否删除：0:未删除，1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群组表';

-- 群成员表
CREATE TABLE `t_im_group_member` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT 'ID，自增主键',
    `group_id`              bigint(20) unsigned   NOT NULL COMMENT '群组ID',
    `user_id`               bigint(20) unsigned   NOT NULL COMMENT '用户ID',
    `join_time`             datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    `created_at`            datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_t_im_group_member_id` (`group_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群成员表';

-- 群聊消息表
CREATE TABLE `t_im_group_message` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT 'ID，自增主键',
    `group_id`              bigint(20) unsigned   NOT NULL COMMENT '群组ID',
    `seq_id`                bigint(20) unsigned   NOT NULL COMMENT '群内单调递增序列号（核心，用于消息同步 可以使用 redis 的 INCR group_id 生成群内单调递增的序列号）',
    `from_user_id`          bigint(20) unsigned   NOT NULL COMMENT '发送者ID',
    `type`                  tinyint(4)            NOT NULL DEFAULT 1 COMMENT '消息类型：1-文本，2-图片',
    `content`               text                  NOT NULL COMMENT '消息内容',
    `extra`                 varchar(1024)                  DEFAULT NULL COMMENT '扩展字段 (@提醒列表, 回复消息ID等, JSON格式)',
    `created_at`            datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted`               tinyint(4)            NOT NULL DEFAULT 0 COMMENT '是否删除：0:未删除，1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_t_im_group_message_id` (`group_id`, `seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群聊消息表';

-- 群成员游标表
CREATE TABLE `t_im_group_member_sync` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT 'ID，自增主键',
    `group_id`              bigint(20) unsigned   NOT NULL COMMENT '群组ID',
    `user_id`               bigint(20) unsigned   NOT NULL COMMENT '用户ID',
    `last_ack_seq`          bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '用户已拉取的最新群消息seq_id',
    `last_read_seq`         bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '用户已真正阅读的群消息seq_id (用于已读回执)',
    `created_at`            datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`            datetime                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_t_im_group_member_sync_id` (`group_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群成员游标表';

-- 私聊消息表
CREATE TABLE `t_im_message` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT 'ID，自增主键',
    `from_user_id`          bigint(20) unsigned   NOT NULL COMMENT '发送者ID',
    `to_user_id`            bigint(20) unsigned   NOT NULL COMMENT '接收者ID',
    `type`                  tinyint(4)            NOT NULL DEFAULT 1 COMMENT '消息类型：1-文本，2-图片',
    `content`               text                  NOT NULL COMMENT '消息内容',
    `is_read`               tinyint(4)            NOT NULL DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
    `read_time`             datetime                       DEFAULT NULL COMMENT '阅读时间',
    `created_at`            datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted`               tinyint(4)            NOT NULL DEFAULT 0 COMMENT '是否删除：0:未删除，1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_t_im_message_id` (`from_user_id`, `to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私聊消息表';

-- 会话表
CREATE TABLE `t_im_conversation` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT 'ID，自增主键',
    `user_id`               bigint(20) unsigned   NOT NULL COMMENT '所属用户ID(谁的会话列表)',
    `chat_type`             tinyint(4)            NOT NULL COMMENT '会话类型：1-单聊，2-群聊',
    `target_id`             bigint(20) unsigned   NOT NULL COMMENT '目标ID(好友ID或群组ID)',
    `last_msg_content`      text                           DEFAULT NULL COMMENT '最后一条消息内容',
    `last_msg_type`         tinyint(4)                     DEFAULT NULL COMMENT '最后一条消息类型：1-文本，2-图片',
    `last_msg_time`         datetime                       DEFAULT NULL COMMENT '最后一条消息发送时间',
    `unread_count`          int(11)                        DEFAULT 0 COMMENT '未读数',
    `created_at`            datetime                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`            datetime                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_t_im_conversation_uct_id` (`user_id`, `chat_type`, `target_id`),
    KEY `idx_t_im_conversation_uu_at` (`user_id`, `updated_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表';
