package com.mountain.web.configuration.security.details;

import com.mountain.web.basic.pojo.po.Role;
import com.mountain.web.basic.pojo.po.User;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全认证用户详情
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 16:16
 **/
@Data
public class SecurityUser implements UserDetails {

    /**
     * 当前登录用户
     */
    private User currentUser;

    /**
     * 当前用户所拥有的角色
     */
    private List<Role> roles;

    public SecurityUser(User currentUser, List<Role> roles) {
        this.currentUser = currentUser;
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(this.roles)) {
            return new ArrayList<>();
        }
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleCode())).toList();
    }

    @Override
    public String getPassword() {
        return currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
