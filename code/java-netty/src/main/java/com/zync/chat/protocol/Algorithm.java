package com.zync.chat.protocol;

import com.zync.chat.common.utils.FastJson2Util;
import com.zync.chat.common.utils.GsonUtil;
import com.zync.chat.common.utils.JacksonUtil;

import java.io.*;

/**
 * 序列化算法实现
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 16:40
 */
public enum Algorithm implements Serializer {

    /**
     * java
     */
    Java {
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
    Jackson {
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
    Gson {
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
    FastJson2 {
        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes) {
            return FastJson2Util.deserialize(clazz, bytes);
        }

        @Override
        public <T> byte[] serialize(T object) {
            return FastJson2Util.serialize(object);
        }
    }
}
