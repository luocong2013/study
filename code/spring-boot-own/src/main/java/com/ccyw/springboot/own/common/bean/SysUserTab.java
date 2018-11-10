package com.ccyw.springboot.own.common.bean;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_user_tab")
public class SysUserTab {
    /**
     * 主键ID，自增长ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * UUID值
     */
    @Column(name = "user_uuid")
    private String userUuid;

    /**
     * 登录账号
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 注册邮箱地址
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登录密码加的盐值
     */
    private String salt;

    /**
     * 是否可用,0：可用，1不可用
     */
    @Column(name = "available_enum")
    private String availableEnum;

    /**
     * 是否删除,0：未删除，1已删除
     */
    @Column(name = "delete_enum")
    private String deleteEnum;

    /**
     * 注册时候的请求IP
     */
    @Column(name = "request_ip")
    private String requestIp;

    /**
     * 注册时候的请求的机子MAC地址
     */
    @Column(name = "request_mac")
    private String requestMac;

    /**
     * 注册时候的请求user agent
     */
    @Column(name = "request_user_agent")
    private String requestUserAgent;

    /**
     * 个人头像图片完整目录路径（不包含域名、端口、工程名）
     */
    @Column(name = "avatar_image_path")
    private String avatarImagePath;

    /**
     * 自我介绍
     */
    @Column(name = "self_introduction")
    private String selfIntroduction;

    /**
     * 性别：0保密，1男性，2女性
     */
    @Column(name = "sex_enum")
    private String sexEnum;

    /**
     * 用户生日（出生日期）
     */
    @Column(name = "user_birthday")
    private Date userBirthday;

    /**
     * 创建时间
     */
    @Column(name = "create_datetime")
    private Date createDatetime;

    /**
     * JPA乐观锁标识字段，必须有值
     */
    @Column(name = "lock_version")
    private Long lockVersion;

    /**
     * 获取主键ID，自增长ID
     *
     * @return id - 主键ID，自增长ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID，自增长ID
     *
     * @param id 主键ID，自增长ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取UUID值
     *
     * @return user_uuid - UUID值
     */
    public String getUserUuid() {
        return userUuid;
    }

    /**
     * 设置UUID值
     *
     * @param userUuid UUID值
     */
    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid == null ? null : userUuid.trim();
    }

    /**
     * 获取登录账号
     *
     * @return login_name - 登录账号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录账号
     *
     * @param loginName 登录账号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 获取注册邮箱地址
     *
     * @return email - 注册邮箱地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置注册邮箱地址
     *
     * @param email 注册邮箱地址
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取昵称
     *
     * @return user_name - 昵称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置昵称
     *
     * @param userName 昵称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取登录密码加的盐值
     *
     * @return salt - 登录密码加的盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置登录密码加的盐值
     *
     * @param salt 登录密码加的盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取是否可用,0：可用，1不可用
     *
     * @return available_enum - 是否可用,0：可用，1不可用
     */
    public String getAvailableEnum() {
        return availableEnum;
    }

    /**
     * 设置是否可用,0：可用，1不可用
     *
     * @param availableEnum 是否可用,0：可用，1不可用
     */
    public void setAvailableEnum(String availableEnum) {
        this.availableEnum = availableEnum == null ? null : availableEnum.trim();
    }

    /**
     * 获取是否删除,0：未删除，1已删除
     *
     * @return delete_enum - 是否删除,0：未删除，1已删除
     */
    public String getDeleteEnum() {
        return deleteEnum;
    }

    /**
     * 设置是否删除,0：未删除，1已删除
     *
     * @param deleteEnum 是否删除,0：未删除，1已删除
     */
    public void setDeleteEnum(String deleteEnum) {
        this.deleteEnum = deleteEnum == null ? null : deleteEnum.trim();
    }

    /**
     * 获取注册时候的请求IP
     *
     * @return request_ip - 注册时候的请求IP
     */
    public String getRequestIp() {
        return requestIp;
    }

    /**
     * 设置注册时候的请求IP
     *
     * @param requestIp 注册时候的请求IP
     */
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp == null ? null : requestIp.trim();
    }

    /**
     * 获取注册时候的请求的机子MAC地址
     *
     * @return request_mac - 注册时候的请求的机子MAC地址
     */
    public String getRequestMac() {
        return requestMac;
    }

    /**
     * 设置注册时候的请求的机子MAC地址
     *
     * @param requestMac 注册时候的请求的机子MAC地址
     */
    public void setRequestMac(String requestMac) {
        this.requestMac = requestMac == null ? null : requestMac.trim();
    }

    /**
     * 获取注册时候的请求user agent
     *
     * @return request_user_agent - 注册时候的请求user agent
     */
    public String getRequestUserAgent() {
        return requestUserAgent;
    }

    /**
     * 设置注册时候的请求user agent
     *
     * @param requestUserAgent 注册时候的请求user agent
     */
    public void setRequestUserAgent(String requestUserAgent) {
        this.requestUserAgent = requestUserAgent == null ? null : requestUserAgent.trim();
    }

    /**
     * 获取个人头像图片完整目录路径（不包含域名、端口、工程名）
     *
     * @return avatar_image_path - 个人头像图片完整目录路径（不包含域名、端口、工程名）
     */
    public String getAvatarImagePath() {
        return avatarImagePath;
    }

    /**
     * 设置个人头像图片完整目录路径（不包含域名、端口、工程名）
     *
     * @param avatarImagePath 个人头像图片完整目录路径（不包含域名、端口、工程名）
     */
    public void setAvatarImagePath(String avatarImagePath) {
        this.avatarImagePath = avatarImagePath == null ? null : avatarImagePath.trim();
    }

    /**
     * 获取自我介绍
     *
     * @return self_introduction - 自我介绍
     */
    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    /**
     * 设置自我介绍
     *
     * @param selfIntroduction 自我介绍
     */
    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction == null ? null : selfIntroduction.trim();
    }

    /**
     * 获取性别：0保密，1男性，2女性
     *
     * @return sex_enum - 性别：0保密，1男性，2女性
     */
    public String getSexEnum() {
        return sexEnum;
    }

    /**
     * 设置性别：0保密，1男性，2女性
     *
     * @param sexEnum 性别：0保密，1男性，2女性
     */
    public void setSexEnum(String sexEnum) {
        this.sexEnum = sexEnum == null ? null : sexEnum.trim();
    }

    /**
     * 获取用户生日（出生日期）
     *
     * @return user_birthday - 用户生日（出生日期）
     */
    public Date getUserBirthday() {
        return userBirthday;
    }

    /**
     * 设置用户生日（出生日期）
     *
     * @param userBirthday 用户生日（出生日期）
     */
    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    /**
     * 获取创建时间
     *
     * @return create_datetime - 创建时间
     */
    public Date getCreateDatetime() {
        return createDatetime;
    }

    /**
     * 设置创建时间
     *
     * @param createDatetime 创建时间
     */
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * 获取JPA乐观锁标识字段，必须有值
     *
     * @return lock_version - JPA乐观锁标识字段，必须有值
     */
    public Long getLockVersion() {
        return lockVersion;
    }

    /**
     * 设置JPA乐观锁标识字段，必须有值
     *
     * @param lockVersion JPA乐观锁标识字段，必须有值
     */
    public void setLockVersion(Long lockVersion) {
        this.lockVersion = lockVersion;
    }
}