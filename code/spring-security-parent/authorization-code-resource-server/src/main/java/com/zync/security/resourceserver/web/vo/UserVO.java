package com.zync.security.resourceserver.web.vo;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户视图类
 * @date 2020/8/28 18:25
 */
public class UserVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;

    public UserVO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
