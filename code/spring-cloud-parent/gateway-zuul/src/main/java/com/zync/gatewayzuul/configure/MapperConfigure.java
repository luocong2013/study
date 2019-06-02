package com.zync.gatewayzuul.configure;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption zuul路由正则匹配
 * @date 2019/6/2 14:55
 */
@SpringBootConfiguration
public class MapperConfigure {

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}");
    }
}
