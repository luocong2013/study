package com.zync.jni.test;

import com.zync.jni.api.MtkUsb;

/**
 * 设备jni测试类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/7/23 22:36
 */
public class MktHidUstTest {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));

        System.loadLibrary("LinuxHidUsb");

        System.out.println("Device count = " + MtkUsb.getInstant().scanDevice());
    }
}
