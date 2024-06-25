package com.zync.boot.mybatisplus.ext;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.zync.boot.mybatisplus.ext.core.handler.DefaultMetaObjectHandler;
import com.zync.boot.mybatisplus.ext.core.injector.BasicSqlInjector;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus ext 自动配置类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/28 17:15
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MybatisPlusAutoConfiguration.class})
@AutoConfigureBefore({MybatisPlusAutoConfiguration.class})
public class MybatisPlusExtAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BasicSqlInjector basicSqlInjector() {
        return new BasicSqlInjector();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultMetaObjectHandler defaultMetaObjectHandler() {
        return new DefaultMetaObjectHandler();
    }

}
