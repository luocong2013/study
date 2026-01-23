package com.mountain.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.mountain.common.common.enums.DatePatternEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * jackson 工具类
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/8 17:14
 */
@Slf4j
@UtilityClass
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DatePatternEnum.STANDARD_FORMAT.getPattern()));
        // 忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 支持java8的时间类型
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * 对象转json格式字符串
     *
     * @param object 对象
     * @return json格式字符串
     */
    public <T> String toJson(T object) {
        if (object == null) {
            return null;
        }
        try {
            return object instanceof String json ? json : OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * json字符串转为目标对象
     *
     * @param json  json字符串
     * @param clazz 目标对象Class
     * @return 目标对象
     */
    public <T> T toBean(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || clazz == null) {
            return null;
        }
        if (clazz.isInstance(json)) {
            return clazz.cast(json);
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * json字符串转为Map
     *
     * @param json json字符串
     * @return Map
     */
    public <K, V> Map<K, V> toMap(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<K, V>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Parse String to Map error", e);
            return null;
        }
    }

    /**
     * json字符串转为List
     *
     * @param json json字符串
     * @return List
     */
    public <T> List<T> toList(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || clazz == null) {
            return null;
        }
        try {
            JavaType valueType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            log.error("Parse String to List error", e);
            return null;
        }
    }

    /**
     * json字符串转为Object
     *
     * @param json          json字符串
     * @param typeReference 自定义类型
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public <T> T toObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json) || typeReference == null) {
            return null;
        }
        if (typeReference.getType().equals(String.class)) {
            return (T) json;
        }
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

}
