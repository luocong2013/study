package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-资源
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_resource")
public class Resource extends BaseEntity<Resource> {
    /**
     * 父ID, 0:一级资源
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 资源名称
     */
    @TableField(value = "resource_name")
    private String resourceName;

    /**
     * 资源编码
     */
    @TableField(value = "resource_code")
    private String resourceCode;

    /**
     * 类型 (0-菜单, 1-按钮, 2-其他)
     */
    @TableField(value = "resource_type")
    private Integer resourceType;

    /**
     * 请求的URL
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求URL类型 (GET, POST, PUT, DELETE)
     */
    @TableField(value = "request_type")
    private String requestType;

    /**
     * 授权类型 (0-角色授权, 1-跟随父资源授权, 2-登录授权)
     */
    @TableField(value = "authorize_type")
    private Integer authorizeType;

    /**
     * 排序号
     */
    @TableField(value = "sort_no")
    private Integer sortNo;
}