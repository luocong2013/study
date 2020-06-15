package com.zync.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.experimental.UtilityClass;

/**
 * @author luocong
 * @description fastjson测试
 * @date 2020/5/25 9:54
 */
@UtilityClass
public class FastJsonUtil {

    public <T> String bean2Json(T t) {
        return JSON.toJSONString(t, SerializerFeature.WriteMapNullValue);
    }

    public <T> T json2Bean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
