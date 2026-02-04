-- 基础设置-用户
CREATE TABLE `basic_user` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `username`              varchar(80)           NOT NULL COMMENT '登录名',
    `password`              varchar(128)          NOT NULL COMMENT '密码',
    `full_name`             varchar(80)           NOT NULL COMMENT '用户中文名',
    `major_department_id`   bigint(20) unsigned   NOT NULL COMMENT '主部门',
    `user_position`         varchar(80)           NOT NULL DEFAULT '' COMMENT '职位',
    `phone`                 varchar(16)           NOT NULL COMMENT '手机号',
    `phone_verify_state`    tinyint(4)            NOT NULL DEFAULT 0 COMMENT '手机验证状态 (0-未验证, 1-已验证)',
    `email`                 varchar(64)           NOT NULL COMMENT '电子邮件',
    `sex`                   tinyint(4)            NOT NULL DEFAULT 0 COMMENT '性别 (0-保密, 1-男, 2-女)',
    `is_enable`             tinyint(4)            NOT NULL DEFAULT 1 COMMENT '账户可用性 (0-禁用, 1-启用)',
    `user_status`           tinyint(4)            NOT NULL DEFAULT 0 COMMENT '用户状态 (0-未激活, 1-正常, 2-异地登录放弃, 3-连续密码错误锁定, 4-手机号发生更换, 5-重置密码)',
    `recent_login_time`     bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '最近一次登录时间',
    `recent_login_ip`       varchar(32)           NOT NULL DEFAULT '' COMMENT '最近一次登录IP',
    `recent_login_city`     varchar(64)           NOT NULL DEFAULT '' COMMENT '最近一次登录城市',

    `create_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改时间',
    `creator_id`            bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建人ID, 0-系统',
    `modifier_id`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改人ID, 0-系统',
    `remark`                varchar(200)          NOT NULL DEFAULT '' COMMENT '备注',
    `deleted`               varchar(30)           NOT NULL DEFAULT '0' COMMENT '0-未删除, 其他值-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_username` (`username`, `deleted`),
    UNIQUE KEY `uniq_phone` (`phone`, `deleted`),
    KEY `idx_dept_id` (`major_department_id`),
    KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-用户';

-- 基础设置-角色
CREATE TABLE `basic_role` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `role_name`             varchar(128)          NOT NULL COMMENT '角色名',
    `role_code`             varchar(128)          NOT NULL COMMENT '角色编码',

    `create_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改时间',
    `creator_id`            bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建人ID, 0-系统',
    `modifier_id`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改人ID, 0-系统',
    `remark`                varchar(200)          NOT NULL DEFAULT '' COMMENT '备注',
    `deleted`               varchar(30)           NOT NULL DEFAULT '0' COMMENT '0-未删除, 其他值-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_code` (`role_code`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-角色';

-- 基础设置-用户角色关系
CREATE TABLE `basic_user_role` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `user_id`               bigint(20) unsigned   NOT NULL COMMENT '用户ID (basic_user.id)',
    `role_id`               bigint(20) unsigned   NOT NULL COMMENT '角色ID (basic_role.id)',
    `role_code`             varchar(128)          NOT NULL COMMENT '角色编码 (basic_role.role_code)',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-用户角色关系';

-- 基础设置-资源
CREATE TABLE `basic_resource` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `parent_id`             bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '父ID, 0:一级资源',
    `resource_name`         varchar(64)           NOT NULL COMMENT '资源名称',
    `resource_code`         varchar(64)           NOT NULL COMMENT '资源编码',
    `resource_type`         tinyint(4)            NOT NULL COMMENT '类型 (0-菜单, 1-按钮, 2-其他)',
    `url`                   varchar(128)          NOT NULL DEFAULT '' COMMENT '请求的URL',
    `request_type`          varchar(8)            NOT NULL COMMENT '请求URL类型 (GET, POST, PUT, DELETE)',
    `authorize_type`        tinyint(4)            NOT NULL DEFAULT 0 COMMENT '授权类型 (0-角色授权, 1-跟随父资源授权, 2-登录授权)',
    `sort_no`               int(11)               NOT NULL DEFAULT 0 COMMENT '排序号',

    `create_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改时间',
    `creator_id`            bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建人ID, 0-系统',
    `modifier_id`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改人ID, 0-系统',
    `remark`                varchar(200)          NOT NULL DEFAULT '' COMMENT '备注',
    `deleted`               varchar(30)           NOT NULL DEFAULT '0' COMMENT '0-未删除, 其他值-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_code` (`resource_code`, `deleted`),
    UNIQUE KEY `uniq_url_type` (`url`, `request_type`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-资源';

-- 基础设置-角色资源关系
CREATE TABLE `basic_role_resource` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `role_id`               bigint(20) unsigned   NOT NULL COMMENT '角色ID (basic_role.id)',
    `resource_code`         varchar(64)           NOT NULL COMMENT '资源编码 (basic_resource.resource_code)',
    PRIMARY KEY (`id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_resource_code` (`resource_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-角色资源关系';

-- 基础设置-部门
CREATE TABLE `basic_department` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `parent_id`             bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '上级部门编码, 0:一级部门',
    `department_name`       varchar(128)          NOT NULL COMMENT '部门名称',
    `department_code`       varchar(128)          NOT NULL COMMENT '部门编码',
    `manager_id`            bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '部门主管ID, 0-未指定',

    `create_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改时间',
    `creator_id`            bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '创建人ID, 0-系统',
    `modifier_id`           bigint(20) unsigned   NOT NULL DEFAULT 0 COMMENT '修改人ID, 0-系统',
    `remark`                varchar(200)          NOT NULL DEFAULT '' COMMENT '备注',
    `deleted`               varchar(30)           NOT NULL DEFAULT '0' COMMENT '0-未删除, 其他值-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_code` (`department_code`, `deleted`),
    KEY `idx_name` (`department_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-部门';

-- 基础设置-用户部门关系
CREATE TABLE `basic_user_department` (
    `id`                    bigint(20) unsigned   NOT NULL COMMENT '主键ID',
    `user_id`               bigint(20) unsigned   NOT NULL COMMENT '用户ID (basic_user.id)',
    `department_id`         bigint(20) unsigned   NOT NULL COMMENT '部门ID (basic_department.id)',
    `department_code`       varchar(80)           NOT NULL COMMENT '部门编码 (basic_department.department_code)',
    `is_major_dept`         tinyint(4)            NOT NULL DEFAULT 0 COMMENT '是否主部门 (0-否, 1-是)',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_department_id` (`department_id`),
    KEY `idx_department_code` (`department_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础设置-用户部门关系';

