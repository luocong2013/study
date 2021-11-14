package com.zync.nacos.nacosdiscoverygateway.locator;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * 动态路由
 *
 * @author luocong
 * @date 2021/7/23 14:44
 */
@SpringBootConfiguration
public class DynamicRouteLocator {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("route-1", r -> r.path("/route1/**").filters(f -> f.stripPrefix(1)).uri("https://www.baidu.com/"))
                .route("route-2", r -> r.query("boy").filters(f -> f.stripPrefix(1).prefixPath("aa")).uri("https://www.baidu.com/"))
                .build();
    }

}
