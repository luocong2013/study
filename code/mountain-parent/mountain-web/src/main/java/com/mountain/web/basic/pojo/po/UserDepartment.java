package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.SimpleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-用户部门关系
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_user_department")
public class UserDepartment extends SimpleEntity<UserDepartment> {

    /**
     * 用户ID (basic_user.id)
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 部门ID (basic_department.id)
     */
    @TableField(value = "department_id")
    private Long departmentId;

    /**
     * 部门编码 (basic_department.department_code)
     */
    @TableField(value = "department_code")
    private String departmentCode;

    /**
     * 是否主部门 (0-否, 1-是)
     */
    @TableField(value = "is_major_dept")
    private Integer isMajorDept;
}