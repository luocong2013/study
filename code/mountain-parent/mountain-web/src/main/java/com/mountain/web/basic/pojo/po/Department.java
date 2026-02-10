package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-部门
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_department")
public class Department extends BaseEntity<Department> {
    /**
     * 上级部门编码, 0:一级部门
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @TableField(value = "department_name")
    private String departmentName;

    /**
     * 部门编码
     */
    @TableField(value = "department_code")
    private String departmentCode;

    /**
     * 部门主管ID, 0-未指定
     */
    @TableField(value = "manager_id")
    private Long managerId;
}