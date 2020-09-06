package com.zync.single.security.jdbc.singlesecurityjdbc.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zync.single.security.jdbc.singlesecurityjdbc.web.mapper.TbPermissionMapper;
import com.zync.single.security.jdbc.singlesecurityjdbc.web.mapper.TbUserMapper;
import com.zync.single.security.jdbc.singlesecurityjdbc.web.model.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户
 * @date 2020/8/29 22:23
 */
@Component
public class JdbcUserDetails implements UserDetailsService {

    @Resource
    private TbUserMapper userMapper;

    @Resource
    private TbPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUser tbUser = userMapper.selectOne(Wrappers.lambdaQuery(TbUser.class)
                .select(TbUser::getId, TbUser::getUsername, TbUser::getPassword)
                .eq(TbUser::getUsername, username)
                .eq(TbUser::getRemoved, 0));
        if (Objects.isNull(tbUser)) {
            // 如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        List<String> permissions = permissionMapper.selectByUserId(tbUser.getId());
        String[] perarray = permissions.stream().filter(StringUtils::isNotBlank).toArray(String[]::new);
        return User.withUsername(tbUser.getUsername()).password(tbUser.getPassword()).authorities(perarray).build();
    }
}
