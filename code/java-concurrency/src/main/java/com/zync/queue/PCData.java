package com.zync.queue;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 共享数据
 * @date 2019/11/16 17:49
 */
public final class PCData {

    private final int intData;

    public PCData(int intData) {
        this.intData = intData;
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}
