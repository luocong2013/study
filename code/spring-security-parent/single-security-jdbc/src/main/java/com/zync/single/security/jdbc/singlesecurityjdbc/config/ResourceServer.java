package com.zync.single.security.jdbc.singlesecurityjdbc.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zync.single.security.jdbc.singlesecurityjdbc.web.mapper.TbPermissionMapper;
import com.zync.single.security.jdbc.singlesecurityjdbc.web.model.TbPermission;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 配置资源服务器
 * @date 2020/9/06 16:01
 */
@SpringBootConfiguration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Resource
    private TbPermissionMapper permissionMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<TbPermission> permissions = permissionMapper.selectList(Wrappers.lambdaQuery(TbPermission.class).select(TbPermission::getEname, TbPermission::getUrl));
        if (CollectionUtils.isEmpty(permissions)) {
            // authorizeRequests: 认证请求配置
            // anyRequest().authenticated(): 所有请求都需要认证通过
            http.authorizeRequests().anyRequest().authenticated();
        }
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        for (TbPermission permission : permissions) {
            registry.antMatchers(permission.getUrl()).hasAuthority(permission.getEname());
        }
        registry.anyRequest().authenticated();
    }
}
