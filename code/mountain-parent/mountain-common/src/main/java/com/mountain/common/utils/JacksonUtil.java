package com.mountain.common.utils;

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
        //对象的所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DatePatternEnum.STANDARD_FORMAT.getPattern()));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //支持java8的时间类型
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
        try {
            return clazz.isInstance(json) ? clazz.cast(json) : OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

}
