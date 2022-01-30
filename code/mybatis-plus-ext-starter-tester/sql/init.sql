CREATE TABLE `user` (
    `id`            bigint(20) unsigned     NOT NULL COMMENT '主键',
    `name`          varchar(50)             NOT NULL COMMENT '姓名',
    `age`           int(11)                 NOT NULL COMMENT '年龄',
    `email`         varchar(100)            NOT NULL COMMENT '邮箱',
    `create_time`   datetime                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`       varchar(30)             NOT NULL DEFAULT '0' COMMENT '0-未删除，其他值-删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';