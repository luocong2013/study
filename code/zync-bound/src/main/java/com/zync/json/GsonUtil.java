package com.zync.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

/**
 * @author luocong
 * @description gson工具类
 * @date 2020/5/25 10:15
 */
@UtilityClass
public class GsonUtil {

    private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public <T> String bean2Json(T t) {
        return gson.toJson(t);
    }

    public <T> T json2Bean(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
