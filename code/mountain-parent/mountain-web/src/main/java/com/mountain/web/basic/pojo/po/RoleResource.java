package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.SimpleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-角色资源关系
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_role_resource")
public class RoleResource extends SimpleEntity<RoleResource> {

    /**
     * 角色ID (basic_role.id)
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 资源编码 (basic_resource.resource_code)
     */
    @TableField(value = "resource_code")
    private String resourceCode;
}