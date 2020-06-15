package com.zync.swx.hutool;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.hutool
 * @description TODO
 * @date 2018-1-4 17:12
 */
public class DataVO {
    private long value;

    public DataVO(long value) {
        this.value = value;
    }

    public long getValue() {
        value++;
        return value;
    }


    /*private final AtomicInteger integer = new AtomicInteger();

    public DataVO(int value) {
        integer.set(value);
    }

    public int getValue() {
        integer.incrementAndGet();
        return integer.get();
    }*/
}
