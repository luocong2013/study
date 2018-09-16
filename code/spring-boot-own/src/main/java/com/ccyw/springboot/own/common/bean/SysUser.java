package com.ccyw.springboot.own.common.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser {
    @Id
    @Column(name = "sys_user_id")
    private Long sysUserId;

    @Column(name = "sys_user_login_name")
    private String sysUserLoginName;

    @Column(name = "sys_user_login_password")
    private String sysUserLoginPassword;

    @Column(name = "sys_user_status")
    private String sysUserStatus;

    @Column(name = "sys_user_is_delete")
    private String sysUserIsDelete;

    @Column(name = "sys_user_register_datetime")
    private Date sysUserRegisterDatetime;

    @Column(name = "sys_user_register_source")
    private String sysUserRegisterSource;

    @Column(name = "sys_user_type")
    private String sysUserType;

    @Column(name = "sys_user_sex")
    private String sysUserSex;

    @Column(name = "sys_user_is_email_active")
    private String sysUserIsEmailActive;

    @Column(name = "sys_user_is_mobile_active")
    private String sysUserIsMobileActive;

    @Column(name = "sys_user_register_type")
    private String sysUserRegisterType;

    @Column(name = "sys_user_pay_passwrod")
    private String sysUserPayPasswrod;

    @Column(name = "sys_user_icon")
    private String sysUserIcon;

    @Column(name = "sys_user_real_name")
    private String sysUserRealName;

    @Column(name = "sys_user_email")
    private String sysUserEmail;

    @Column(name = "sys_user_mobile")
    private String sysUserMobile;

    @Column(name = "sys_user_weibo_id")
    private String sysUserWeiboId;

    @Column(name = "sys_user_qq_id")
    private String sysUserQqId;

    /**
     * @return sys_user_id
     */
    public Long getSysUserId() {
        return sysUserId;
    }

    /**
     * @param sysUserId
     */
    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return sys_user_login_name
     */
    public String getSysUserLoginName() {
        return sysUserLoginName;
    }

    /**
     * @param sysUserLoginName
     */
    public void setSysUserLoginName(String sysUserLoginName) {
        this.sysUserLoginName = sysUserLoginName == null ? null : sysUserLoginName.trim();
    }

    /**
     * @return sys_user_login_password
     */
    public String getSysUserLoginPassword() {
        return sysUserLoginPassword;
    }

    /**
     * @param sysUserLoginPassword
     */
    public void setSysUserLoginPassword(String sysUserLoginPassword) {
        this.sysUserLoginPassword = sysUserLoginPassword == null ? null : sysUserLoginPassword.trim();
    }

    /**
     * @return sys_user_status
     */
    public String getSysUserStatus() {
        return sysUserStatus;
    }

    /**
     * @param sysUserStatus
     */
    public void setSysUserStatus(String sysUserStatus) {
        this.sysUserStatus = sysUserStatus == null ? null : sysUserStatus.trim();
    }

    /**
     * @return sys_user_is_delete
     */
    public String getSysUserIsDelete() {
        return sysUserIsDelete;
    }

    /**
     * @param sysUserIsDelete
     */
    public void setSysUserIsDelete(String sysUserIsDelete) {
        this.sysUserIsDelete = sysUserIsDelete == null ? null : sysUserIsDelete.trim();
    }

    /**
     * @return sys_user_register_datetime
     */
    public Date getSysUserRegisterDatetime() {
        return sysUserRegisterDatetime;
    }

    /**
     * @param sysUserRegisterDatetime
     */
    public void setSysUserRegisterDatetime(Date sysUserRegisterDatetime) {
        this.sysUserRegisterDatetime = sysUserRegisterDatetime;
    }

    /**
     * @return sys_user_register_source
     */
    public String getSysUserRegisterSource() {
        return sysUserRegisterSource;
    }

    /**
     * @param sysUserRegisterSource
     */
    public void setSysUserRegisterSource(String sysUserRegisterSource) {
        this.sysUserRegisterSource = sysUserRegisterSource == null ? null : sysUserRegisterSource.trim();
    }

    /**
     * @return sys_user_type
     */
    public String getSysUserType() {
        return sysUserType;
    }

    /**
     * @param sysUserType
     */
    public void setSysUserType(String sysUserType) {
        this.sysUserType = sysUserType == null ? null : sysUserType.trim();
    }

    /**
     * @return sys_user_sex
     */
    public String getSysUserSex() {
        return sysUserSex;
    }

    /**
     * @param sysUserSex
     */
    public void setSysUserSex(String sysUserSex) {
        this.sysUserSex = sysUserSex == null ? null : sysUserSex.trim();
    }

    /**
     * @return sys_user_is_email_active
     */
    public String getSysUserIsEmailActive() {
        return sysUserIsEmailActive;
    }

    /**
     * @param sysUserIsEmailActive
     */
    public void setSysUserIsEmailActive(String sysUserIsEmailActive) {
        this.sysUserIsEmailActive = sysUserIsEmailActive == null ? null : sysUserIsEmailActive.trim();
    }

    /**
     * @return sys_user_is_mobile_active
     */
    public String getSysUserIsMobileActive() {
        return sysUserIsMobileActive;
    }

    /**
     * @param sysUserIsMobileActive
     */
    public void setSysUserIsMobileActive(String sysUserIsMobileActive) {
        this.sysUserIsMobileActive = sysUserIsMobileActive == null ? null : sysUserIsMobileActive.trim();
    }

    /**
     * @return sys_user_register_type
     */
    public String getSysUserRegisterType() {
        return sysUserRegisterType;
    }

    /**
     * @param sysUserRegisterType
     */
    public void setSysUserRegisterType(String sysUserRegisterType) {
        this.sysUserRegisterType = sysUserRegisterType == null ? null : sysUserRegisterType.trim();
    }

    /**
     * @return sys_user_pay_passwrod
     */
    public String getSysUserPayPasswrod() {
        return sysUserPayPasswrod;
    }

    /**
     * @param sysUserPayPasswrod
     */
    public void setSysUserPayPasswrod(String sysUserPayPasswrod) {
        this.sysUserPayPasswrod = sysUserPayPasswrod == null ? null : sysUserPayPasswrod.trim();
    }

    /**
     * @return sys_user_icon
     */
    public String getSysUserIcon() {
        return sysUserIcon;
    }

    /**
     * @param sysUserIcon
     */
    public void setSysUserIcon(String sysUserIcon) {
        this.sysUserIcon = sysUserIcon == null ? null : sysUserIcon.trim();
    }

    /**
     * @return sys_user_real_name
     */
    public String getSysUserRealName() {
        return sysUserRealName;
    }

    /**
     * @param sysUserRealName
     */
    public void setSysUserRealName(String sysUserRealName) {
        this.sysUserRealName = sysUserRealName == null ? null : sysUserRealName.trim();
    }

    /**
     * @return sys_user_email
     */
    public String getSysUserEmail() {
        return sysUserEmail;
    }

    /**
     * @param sysUserEmail
     */
    public void setSysUserEmail(String sysUserEmail) {
        this.sysUserEmail = sysUserEmail == null ? null : sysUserEmail.trim();
    }

    /**
     * @return sys_user_mobile
     */
    public String getSysUserMobile() {
        return sysUserMobile;
    }

    /**
     * @param sysUserMobile
     */
    public void setSysUserMobile(String sysUserMobile) {
        this.sysUserMobile = sysUserMobile == null ? null : sysUserMobile.trim();
    }

    /**
     * @return sys_user_weibo_id
     */
    public String getSysUserWeiboId() {
        return sysUserWeiboId;
    }

    /**
     * @param sysUserWeiboId
     */
    public void setSysUserWeiboId(String sysUserWeiboId) {
        this.sysUserWeiboId = sysUserWeiboId == null ? null : sysUserWeiboId.trim();
    }

    /**
     * @return sys_user_qq_id
     */
    public String getSysUserQqId() {
        return sysUserQqId;
    }

    /**
     * @param sysUserQqId
     */
    public void setSysUserQqId(String sysUserQqId) {
        this.sysUserQqId = sysUserQqId == null ? null : sysUserQqId.trim();
    }
}