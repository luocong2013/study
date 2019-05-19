# 创建数据库，并创建权限用户
CREATE DATABASE `ssm` CHARACTER SET utf8mb4;
CREATE USER 'ssm'@'%' IDENTIFIED BY 'ssm';
GRANT ALL PRIVILEGES ON ssm.* TO 'ssm'@'%';
FLUSH PRIVILEGES;


# 创建表
CREATE TABLE `sys_user` (
  `sys_user_id` bigint(20) NOT NULL,
  `sys_user_login_name` varchar(50) NOT NULL,
  `sys_user_login_password` varchar(50) NOT NULL,
  `sys_user_status` varchar(1) NOT NULL,
  `sys_user_is_delete` varchar(1) NOT NULL,
  `sys_user_register_datetime` datetime NOT NULL,
  `sys_user_register_source` varchar(1) NOT NULL,
  `sys_user_type` varchar(1) NOT NULL,
  `sys_user_sex` varchar(1) NOT NULL,
  `sys_user_is_email_active` varchar(1) NOT NULL,
  `sys_user_is_mobile_active` varchar(1) NOT NULL,
  `sys_user_register_type` varchar(1) NOT NULL,
  `sys_user_pay_passwrod` varchar(50) DEFAULT NULL,
  `sys_user_icon` varchar(100) DEFAULT NULL,
  `sys_user_real_name` varchar(20) DEFAULT NULL,
  `sys_user_email` varchar(50) DEFAULT NULL,
  `sys_user_mobile` varchar(20) DEFAULT NULL,
  `sys_user_weibo_id` varchar(36) DEFAULT NULL,
  `sys_user_qq_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# 创建表数据
insert  into `sys_user`(`sys_user_id`,`sys_user_login_name`,`sys_user_login_password`,`sys_user_status`,`sys_user_is_delete`,`sys_user_register_datetime`,`sys_user_register_source`,`sys_user_type`,`sys_user_sex`,`sys_user_is_email_active`,`sys_user_is_mobile_active`,`sys_user_register_type`,`sys_user_pay_passwrod`,`sys_user_icon`,`sys_user_real_name`,`sys_user_email`,`sys_user_mobile`,`sys_user_weibo_id`,`sys_user_qq_id`) values (1,'YouMeek1','e10adc3949ba59abbe56e057f20f883e','0','N','2016-02-24 00:12:23','0','0','0','Y','Y','0','e10adc3949ba59abbe56e057f20f883e','','张觉恩1','363379441@qq.com','13800000001','','');
insert  into `sys_user`(`sys_user_id`,`sys_user_login_name`,`sys_user_login_password`,`sys_user_status`,`sys_user_is_delete`,`sys_user_register_datetime`,`sys_user_register_source`,`sys_user_type`,`sys_user_sex`,`sys_user_is_email_active`,`sys_user_is_mobile_active`,`sys_user_register_type`,`sys_user_pay_passwrod`,`sys_user_icon`,`sys_user_real_name`,`sys_user_email`,`sys_user_mobile`,`sys_user_weibo_id`,`sys_user_qq_id`) values (2,'YouMeek2','e10adc3949ba59abbe56e057f20f883e','0','N','2016-02-24 00:12:23','0','0','0','Y','Y','0','e10adc3949ba59abbe56e057f20f883e','','张觉恩2','363379442@qq.com','13800000002','','');
insert  into `sys_user`(`sys_user_id`,`sys_user_login_name`,`sys_user_login_password`,`sys_user_status`,`sys_user_is_delete`,`sys_user_register_datetime`,`sys_user_register_source`,`sys_user_type`,`sys_user_sex`,`sys_user_is_email_active`,`sys_user_is_mobile_active`,`sys_user_register_type`,`sys_user_pay_passwrod`,`sys_user_icon`,`sys_user_real_name`,`sys_user_email`,`sys_user_mobile`,`sys_user_weibo_id`,`sys_user_qq_id`) values (3,'YouMeek3','e10adc3949ba59abbe56e057f20f883e','0','N','2016-02-24 00:12:23','0','0','0','Y','Y','0','e10adc3949ba59abbe56e057f20f883e','','张觉恩3','363379443@qq.com','13800000003','','');
insert  into `sys_user`(`sys_user_id`,`sys_user_login_name`,`sys_user_login_password`,`sys_user_status`,`sys_user_is_delete`,`sys_user_register_datetime`,`sys_user_register_source`,`sys_user_type`,`sys_user_sex`,`sys_user_is_email_active`,`sys_user_is_mobile_active`,`sys_user_register_type`,`sys_user_pay_passwrod`,`sys_user_icon`,`sys_user_real_name`,`sys_user_email`,`sys_user_mobile`,`sys_user_weibo_id`,`sys_user_qq_id`) values (4,'YouMeek4','e10adc3949ba59abbe56e057f20f883e','0','N','2016-02-24 00:12:23','0','0','0','Y','Y','0','e10adc3949ba59abbe56e057f20f883e','','张觉恩4','363379444@qq.com','13800000004','','');
insert  into `sys_user`(`sys_user_id`,`sys_user_login_name`,`sys_user_login_password`,`sys_user_status`,`sys_user_is_delete`,`sys_user_register_datetime`,`sys_user_register_source`,`sys_user_type`,`sys_user_sex`,`sys_user_is_email_active`,`sys_user_is_mobile_active`,`sys_user_register_type`,`sys_user_pay_passwrod`,`sys_user_icon`,`sys_user_real_name`,`sys_user_email`,`sys_user_mobile`,`sys_user_weibo_id`,`sys_user_qq_id`) values (5,'YouMeek5','e10adc3949ba59abbe56e057f20f883e','0','N','2016-02-24 00:12:23','0','0','0','Y','Y','0','e10adc3949ba59abbe56e057f20f883e','','张觉恩5','363379445@qq.com','13800000005','','');

CREATE TABLE `learn` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`cupsize` VARCHAR(50) NULL DEFAULT '0',
	`age` SMALLINT(6) NULL DEFAULT '0',
	`money` DOUBLE NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE='utf8mb4_0900_ai_ci';

insert into `learn`(`cupsize`, `age`, `money`) values ('C', 23, 500);

CREATE TABLE `sys_user_tab` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增长',
	`user_uuid` VARCHAR(32) NULL DEFAULT NULL COMMENT 'UUID值',
	`login_name` VARCHAR(300) NULL DEFAULT NULL COMMENT '登录账号',
	`email` VARCHAR(300) NULL DEFAULT NULL COMMENT '注册邮箱地址',
	`password` VARCHAR(300) NULL DEFAULT NULL COMMENT '密码',
	`user_name` VARCHAR(300) NULL DEFAULT NULL COMMENT '昵称',
	`salt` VARCHAR(300) NULL DEFAULT NULL COMMENT '登录密码加的盐值',
	`available_enum` VARCHAR(300) NULL DEFAULT NULL COMMENT '是否可用,0：可用，1不可用',
	`delete_enum` VARCHAR(300) NULL DEFAULT NULL COMMENT '是否删除,0：未删除，1已删除',
	`request_ip` VARCHAR(300) NULL DEFAULT NULL COMMENT '注册时候的请求IP',
	`request_mac` VARCHAR(300) NULL DEFAULT NULL COMMENT '注册时候的请求的机子MAC地址',
	`request_user_agent` VARCHAR(300) NULL DEFAULT NULL COMMENT '注册时候的请求user agent',
	`avatar_image_path` VARCHAR(300) NULL DEFAULT NULL COMMENT '个人头像图片完整目录路径（不包含域名、端口、工程名）',
	`self_introduction` VARCHAR(900) NULL DEFAULT NULL COMMENT '自我介绍',
	`sex_enum` VARCHAR(300) NULL DEFAULT NULL COMMENT '性别：0保密，1男性，2女性',
	`user_birthday` DATE NULL DEFAULT NULL COMMENT '用户生日（出生日期）',
	`create_datetime` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`lock_version` BIGINT(20) NULL DEFAULT NULL COMMENT 'JPA乐观锁标识字段，必须有值',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE='utf8mb4_0900_ai_ci';


