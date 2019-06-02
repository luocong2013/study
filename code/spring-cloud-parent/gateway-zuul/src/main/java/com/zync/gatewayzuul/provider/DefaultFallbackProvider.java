package com.zync.gatewayzuul.provider;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Zuul容错与回退
 * @date 2019/6/2 15:02
 */
@Component
public class DefaultFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        // 匹配微服务名字，* 表示匹配所有
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                // 当fallback时返回给调用者的状态码
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                // 状态码数值
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                // 状态码的文本
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                // 响应体
                return new ByteArrayInputStream("default fallback".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                // 设置headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_HTML);
                return headers;
            }
        };
    }
}
