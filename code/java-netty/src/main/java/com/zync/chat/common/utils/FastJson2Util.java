package com.zync.chat.common.utils;

import com.alibaba.fastjson2.JSON;
import lombok.experimental.UtilityClass;

/**
 * fastjson2工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 16:54
 */
@UtilityClass
public class FastJson2Util {

    /**
     * 反序列化
     *
     * @param clazz
     * @param bytes
     * @return
     * @param <T>
     */
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     * @param <T>
     */
    public <T> byte[] serialize(T object) {
        return JSON.toJSONBytes(object);
    }

}
