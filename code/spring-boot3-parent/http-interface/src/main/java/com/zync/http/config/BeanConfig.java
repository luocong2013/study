package com.zync.http.config;

import com.zync.http.api.DemoApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Bean配置文件
 *
 * @author luocong
 * @version v1.0
 * @date 2022/12/12 20:52
 */
@Configuration
public class BeanConfig {

    @Bean
    public DemoApi demoApi() {
        WebClient client = WebClient.builder().baseUrl("http://t.weather.sojson.com/").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(DemoApi.class);
    }

}
