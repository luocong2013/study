package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.SimpleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-用户角色关系
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_user_role")
public class UserRole extends SimpleEntity<UserRole> {

    /**
     * 用户ID (basic_user.id)
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色ID (basic_role.id)
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 角色编码 (basic_role.role_code)
     */
    @TableField(value = "role_code")
    private String roleCode;
}