package com.mountain.common.configuration.redis.serializer;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * redis key统一前缀
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/28 15:32
 **/
@AllArgsConstructor
public class PrefixStringRedisSerializer extends StringRedisSerializer {

    private final String prefix;

    @Override
    public byte[] serialize(String value) {
        return (value == null ? null : (prefix + value).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, StandardCharsets.UTF_8).replace(prefix, StringUtils.EMPTY));
    }
}
