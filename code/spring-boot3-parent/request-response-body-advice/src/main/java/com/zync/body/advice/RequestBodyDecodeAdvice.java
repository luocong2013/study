package com.zync.body.advice;

import com.zync.body.annotation.DecodeBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;

/**
 * RequestBody解码
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 18:12
 */
@Slf4j
@RestControllerAdvice
public class RequestBodyDecodeAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(DecodeBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] payload = StreamUtils.copyToByteArray(inputMessage.getBody());
        log.info("加密 Payload: {}", new String(payload));

        // 解码为原始数据
        byte[] rawPayload = Base64.getDecoder().decode(payload);

        log.info("原始 Payload: {}", new String(rawPayload));
        return new MappingJacksonInputMessage(new ByteArrayInputStream(rawPayload), inputMessage.getHeaders());
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("After Body: {}", body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("Empty Body: {}", body);
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }
}
