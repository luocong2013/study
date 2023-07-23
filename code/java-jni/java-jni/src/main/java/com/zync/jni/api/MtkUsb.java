package com.zync.jni.api;

/**
 * 设备
 *
 * @author luocong
 * @version v1.0
 * @date 2023/7/23 22:35
 */
public class MtkUsb {

    private final static MtkUsb mtkUsb = new MtkUsb();

    private MtkUsb() {}

    public static MtkUsb getInstant() {
        return mtkUsb;
    }

    private final MtkUsbJNI mtkUsbJNI = new MtkUsbJNI();

    public int scanDevice() {
        return mtkUsbJNI.scanDevice();
    }

}
