package com.zync.chat.protocol;

/**
 * 用于扩展序列化、反序列化算法
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 16:37
 */
public interface Serializer {

    /**
     * 反序列化方法
     *
     * @param clazz
     * @param bytes
     * @return
     * @param <T>
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化方法
     *
     * @param object
     * @return
     * @param <T>
     */
    <T> byte[] serialize(T object);
}
