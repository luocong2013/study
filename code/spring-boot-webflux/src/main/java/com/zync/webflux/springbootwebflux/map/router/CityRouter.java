package com.zync.webflux.springbootwebflux.map.router;

import com.zync.webflux.springbootwebflux.map.handler.CityHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 城市路由
 * @date 2020/3/7 16:19
 */
@SpringBootConfiguration
public class CityRouter {

    /**
     * RouterFunctions对请求路由处理，即将请求路由到处理器，这里将一个GET请求/hello
     * 路由到处理器cityHandler的helloCity方法上，和SpringMVC模式下的HandleMapping的作用类似
     * @param cityHandler
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> routeCity(CityHandler cityHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/hello")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                cityHandler::helloCity);
    }
}
