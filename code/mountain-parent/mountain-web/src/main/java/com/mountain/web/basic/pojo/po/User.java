package com.mountain.web.basic.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mountain.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础设置-用户
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "basic_user")
public class User extends BaseEntity<User> {
    /**
     * 登录名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户中文名
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 主部门
     */
    @TableField(value = "major_department_id")
    private Long majorDepartmentId;

    /**
     * 职位
     */
    @TableField(value = "user_position")
    private String userPosition;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 手机验证状态 (0-未验证, 1-已验证)
     */
    @TableField(value = "phone_verify_state")
    private Integer phoneVerifyState;

    /**
     * 电子邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 性别 (0-保密, 1-男, 2-女)
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 账户可用性 (0-禁用, 1-启用)
     */
    @TableField(value = "is_enable")
    private Integer isEnable;

    /**
     * 用户状态 (0-未激活, 1-正常, 2-异地登录放弃, 3-连续密码错误锁定, 4-手机号发生更换, 5-重置密码)
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     * 最近一次登录时间
     */
    @TableField(value = "recent_login_time")
    private Long recentLoginTime;

    /**
     * 最近一次登录IP
     */
    @TableField(value = "recent_login_ip")
    private String recentLoginIp;

    /**
     * 最近一次登录城市
     */
    @TableField(value = "recent_login_city")
    private String recentLoginCity;
}