package com.zync.chat.protocol;

import com.zync.chat.common.utils.FastJson2Util;
import com.zync.chat.common.utils.GsonUtil;
import com.zync.chat.common.utils.JacksonUtil;
import lombok.AllArgsConstructor;

import java.io.*;
import java.util.Arrays;

/**
 * 序列化算法实现
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 16:40
 */
@AllArgsConstructor
public enum Algorithm implements Serializer {

    /**
     * java
     */
    Java(0) {
        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return (T) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("反序列化失败", e);
            }
        }

        @Override
        public <T> byte[] serialize(T object) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(object);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("序列化失败", e);
            }
        }
    },

    /**
     * jackson
     */
    Jackson(1) {
        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes) {
            return JacksonUtil.deserialize(clazz, bytes);
        }

        @Override
        public <T> byte[] serialize(T object) {
            return JacksonUtil.serialize(object);
        }
    },

    /**
     * gson
     */
    Gson(2) {
        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes) {
            return GsonUtil.deserialize(clazz, bytes);
        }

        @Override
        public <T> byte[] serialize(T object) {
            return GsonUtil.serialize(object);
        }
    },

    /**
     * fastjson2
     */
    FastJson2(3) {
        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes) {
            return FastJson2Util.deserialize(clazz, bytes);
        }

        @Override
        public <T> byte[] serialize(T object) {
            return FastJson2Util.serialize(object);
        }
    };


    private final int serializerAlgorithm;

    public int getSerializerAlgorithm() {
        return serializerAlgorithm;
    }

    /**
     * 获取序列化算法
     *
     * @param serializerAlgorithm
     * @return
     */
    public static Algorithm getAlgorithm(int serializerAlgorithm) {
        return Arrays.stream(Algorithm.values())
                .filter(item -> serializerAlgorithm == item.getSerializerAlgorithm())
                .findFirst()
                .orElse(Java);
    }
}
