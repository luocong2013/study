package com.zync.jni.api;

/**
 * 设备native层
 *
 * @author luocong
 * @version v1.0
 * @date 2023/7/23 22:34
 */
public class MtkUsbJNI {


    /**
     * 调用C++获取设备数量
     *
     * @return
     */
    public native int scanDevice();

}
