package com.mountain.common.configuration.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 * 自定义 Jackson2ObjectMapperBuilderCustomizer 处理返回的json数据
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/9 10:50
 */
@Component
public class CustomizeJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        builder.featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        builder.featuresToEnable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        builder.serializerByType(Long.class, ToStringSerializer.instance);
    }

}
