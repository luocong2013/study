package com.zync.chat.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zync.chat.common.consts.Const;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

/**
 * gson 工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 17:19
 */
@UtilityClass
public class GsonUtil {

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat(Const.DATA_FORMAT_PATTERN).create();

    /**
     * 反序列化方法
     *
     * @param clazz
     * @param bytes
     * @return
     * @param <T>
     */
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);
        return GSON.fromJson(json, clazz);
    }

    /**
     * 序列化方法
     *
     * @param object
     * @return
     * @param <T>
     */
    public <T> byte[] serialize(T object) {
        String json = GSON.toJson(object);
        return json.getBytes(StandardCharsets.UTF_8);
    }

}
