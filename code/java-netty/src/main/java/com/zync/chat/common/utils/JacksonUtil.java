package com.zync.chat.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zync.chat.common.consts.Const;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * jackson 工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 17:04
 */
@UtilityClass
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        OBJECT_MAPPER.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //允许将JSON空字符串强制转换为null对象值
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        //允许单个数值当做数组处理
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        //禁止重复键, 抛出异常
        OBJECT_MAPPER.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        OBJECT_MAPPER.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //使用null表示集合类型字段是时不抛异常
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //对象为空时不抛异常
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //允许在JSON中使用c/c++风格注释
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_COMMENTS);
        //允许未知字段
        OBJECT_MAPPER.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        //在JSON中允许未引用的字段名
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        //时间格式
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(Const.DATA_FORMAT_PATTERN));
        //识别单引号
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
    }


    /**
     * 反序列化
     *
     * @param clazz
     * @param bytes
     * @return
     * @param <T>
     */
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     * @param <T>
     */
    public <T> byte[] serialize(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化失败", e);
        }
    }
}
