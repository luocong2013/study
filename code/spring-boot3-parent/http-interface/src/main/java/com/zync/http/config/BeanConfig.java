package com.zync.http.config;

import com.zync.http.api.DemoApi;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

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
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofSeconds(3))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(3)));

        WebClient client = WebClient.builder().baseUrl("http://t.weather.sojson.com/").clientConnector(new ReactorClientHttpConnector(httpClient)).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(DemoApi.class);
    }

}
