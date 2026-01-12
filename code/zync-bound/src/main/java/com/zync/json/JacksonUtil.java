package com.zync.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author luocong
 * @description jackson工具类
 * @date 2020/5/25 10:39
 */
@Slf4j
@UtilityClass
public class JacksonUtil {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        OBJECT_MAPPER.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        // 允许将JSON空字符串强制转换为null对象值
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 允许单个数值当做数组处理
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        // 禁止重复键, 抛出异常
        OBJECT_MAPPER.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        // 禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        OBJECT_MAPPER.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        // 有属性不能映射的时候不报错
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 使用null表示集合类型字段是时不抛异常
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        // 对象为空时不抛异常
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 允许在JSON中使用c/c++风格注释
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_COMMENTS);
        // 强制转义非ascii字符
        OBJECT_MAPPER.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        // 允许未知字段
        OBJECT_MAPPER.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        // 在JSON中允许未引用的字段名
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        // 时间格式
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 识别单引号
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        // 识别特殊字符
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        // 识别Java8时间
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
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
            return object instanceof String ? (String) object : OBJECT_MAPPER.writeValueAsString(object);
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
        if (String.class.isAssignableFrom(clazz)) {
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
