package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-角色
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_role")
public class Role extends BaseEntity<Role> {
    /**
     * 角色名
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;
}