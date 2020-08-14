## Spring Security学习

### 认证流程

UsernamePasswordAuthenticationFilter                 用户名密码过滤器
ProviderManager                                      Provider管理器
AuthenticationProvider                               认证接口实现（supports判断是否支持认证、authenticate进行认证）
AbstractUserDetailsAuthenticationProvider            认证抽象类
DaoAuthenticationProvider                            认证具体实现类（retrieveUser）

UserDetailsService                                   加载用户
PasswordEncoder                                      密码编码器（常用的：BCryptPasswordEncoder/Pbkdf2PasswordEncoder/SCryptPasswordEncoder）


### 授权工作原理：（投票机制）
AccessDecisionManager                                访问决策管理器
    AffirmativeBased                                 默认使用这个
        1）只要有人投票为ACCESS_GRANTED则同意用户进行访问
        2）如果全部放弃（投票为ACCESS_ABSTAIN）也表示通过
        3）如果没有一个人投赞成票，但是有人投反对票（投票为ACCESS_DENIED），则抛出AccessDeniedException
    ConsensusBased
        1）如果赞成票多于反对票则表示通过
        2）反过来，如果反对票多于赞成票则抛出AccessDeniedException
        3）如果赞成票与反对票相同且不等于0，并且属性allowIfEqualGrantedDeniedDecisions（默认为true）的值为true，则表示通过，否则将抛出AccessDeniedException
        4）如果所有的都弃权了，则将视参数 allowIfAllAbstainDecisions（默认为false）的值而定，如果该值为true，则表示通过，否则将抛出AccessDeniedException
    UnanimousBased
        1）如果受保护对象配置的某一个ConfigAttribute被任意的AccessDecisionVoter反对了，则将抛出AccessDeniedException
        2）如果没有反对票，但是有赞成票，则表示通过
        3）如果全部弃权了，则将视参数 allowIfAllAbstainDecisions（默认为false）的值而定，true则通过，false则抛出AccessDeniedException

### Spring Security会话控制

可以通过以下选项准确控制会话何时创建以及Spring Security如何与之交互

| 机制       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| always     | 如果没有session存在就创建一个                                |
| ifRequired | 如果需要就创建一个session（默认）登录时                      |
| never      | Spring Security将不会创建session，但是如果应用中其他地方创建了session，那么Spring Security将会使用它 |
| stateless  | Spring Security将绝对不会创建session，也不会使用session，适用于无状态认证机制（分布式系统以Token方式保存会话） |



### 测试脚本


```sql
# 用户表
DROP TABLE `t_user`;
CREATE TABLE `t_user` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`username` VARCHAR(100) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`fullname` VARCHAR(300) NOT NULL COMMENT '用户姓名',
	`mobile` VARCHAR(11) DEFAULT NULL COMMENT '手机号',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=DYNAMIC;
INSERT INTO t_user (username, password, fullname, mobile) VALUES ('zhangsan', '$2y$10$UA9kCc1yWGjKsJ8zfRl/ROQk77TloNLnAjVR2G2pKXwDb7qdFUk6q', '张三', '12256728271');
INSERT INTO t_user (username, password, fullname, mobile) VALUES ('lisi', '$2y$10$qr0UOfx/j7IJZQex6drEHuwfokFsN/PzhJnDRkHs549KT5vZXgUSK', '李四', '18451252645');

# 角色表
DROP TABLE `t_role`;
CREATE TABLE `t_role` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` VARCHAR(300) DEFAULT NULL,
    `description` VARCHAR(300) DEFAULT NULL,
    `create_time` DATETIME DEFAULT NULL,
    `update_time` DATETIME DEFAULT NULL,
    `status` CHAR(1) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unique_role_name` (`role_name`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO t_role (role_name, description, create_time, update_time, status) VALUES ('管理员', '系统管理员，系统的最大权限管理者', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '1');

# 用户角色关系表
CREATE TABLE `t_user_role` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色关系ID',
    `user_id` BIGINT(20) NOT NULL,
    `role_id` BIGINT(20) NOT NULL,
    `create_time` DATETIME DEFAULT NULL,
    `creator` VARCHAR(100) DEFAULT NULL COMMENT '创建者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO t_user_role (user_id, role_id, create_time, creator) VALUES (1, 1, CURRENT_TIMESTAMP(), 'admin');

# 权限表
CREATE TABLE `t_permission` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `code` VARCHAR(100) NOT NULL COMMENT '权限code',
    `description` VARCHAR(300) DEFAULT NULL COMMENT '权限描述',
    `url` VARCHAR(300) DEFAULT NULL COMMENT '权限对应的URL',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO t_permission (code, description, url) VALUES ('p1', '测试资源1', '/r/r1'), ('p2', '测试资源2', '/r/r2');

# 角色权限表
CREATE TABLE `t_role_permission` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限关系ID',
    `role_id` BIGINT(20) NOT NULL,
    `permission_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO t_role_permission (role_id, permission_id) VALUES (1, 1), (1, 2);
```