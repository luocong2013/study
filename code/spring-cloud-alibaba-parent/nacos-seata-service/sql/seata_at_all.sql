-- 库存（storage）
DROP SCHEMA IF EXISTS seata_at_storage;
CREATE SCHEMA seata_at_storage;
USE seata_at_storage;

CREATE TABLE IF NOT EXISTS `storage` (
   `id`             bigint(11)   NOT NULL AUTO_INCREMENT,
   `name`           varchar(100) NOT NULL COMMENT '产品名称',
   `num`            bigint(11)   NOT NULL COMMENT '数量',
   `create_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `price`          bigint(10)   NOT NULL COMMENT '单价',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '库存表';
INSERT INTO `storage`(`name`, `num`, `price`) VALUES ('Netty实战', 100, 100);
INSERT INTO `storage`(`name`, `num`, `price`) VALUES ('Java并发编程', 20, 55);

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log` (
   `branch_id`      BIGINT       NOT NULL COMMENT 'branch transaction id',
   `xid`            VARCHAR(128) NOT NULL COMMENT 'global transaction id',
   `context`        VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
   `rollback_info`  LONGBLOB     NOT NULL COMMENT 'rollback info',
   `log_status`     INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
   `log_created`    DATETIME(6)  NOT NULL COMMENT 'create datetime',
   `log_modified`   DATETIME(6)  NOT NULL COMMENT 'modify datetime',
   UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';


-- 账户（account）
DROP SCHEMA IF EXISTS seata_at_account;
CREATE SCHEMA seata_at_account;
USE seata_at_account;

CREATE TABLE IF NOT EXISTS `account` (
    `id`             bigint(11)   NOT NULL AUTO_INCREMENT,
    `user_id`        varchar(32)  NOT NULL COMMENT '用户ID',
    `money`          bigint(11)   NOT NULL COMMENT '余额',
    `create_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '账户表';
INSERT INTO `account` (`user_id`, `money`) VALUES('zhangsan', 1000);
INSERT INTO `account` (`user_id`, `money`) VALUES('lisi', 200);

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log` (
    `branch_id`      BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`            VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`        VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info`  LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`     INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`    DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`   DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';


-- 订单（order）
DROP SCHEMA IF EXISTS seata_at_order;
CREATE SCHEMA seata_at_order;
USE seata_at_order;

CREATE TABLE IF NOT EXISTS `t_order` (
     `id`             bigint(11)  NOT NULL AUTO_INCREMENT,
     `product_id`     bigint(11)  NOT NULL COMMENT '产品ID',
     `num`            bigint(11)  NOT NULL COMMENT '购买数量',
     `user_id`        varchar(32) NOT NULL COMMENT '用户ID',
     `create_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `status`         int(1)      NOT NULL COMMENT '订单状态, 1-未付款 2-已付款 3-已完成',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '订单表';

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log` (
    `branch_id`      BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`            VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`        VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info`  LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`     INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`    DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`   DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';

