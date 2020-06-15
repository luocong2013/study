package com.zync.nio.netty.demo.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luoc
 * @version V1.0
 * @package com.zync.nio.netty.demo.time
 * @description: TODO
 * @date 2017/12/10 18:27
 */
public class UnixTime {
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((value() - 2208988800L) * 1000L));
    }
}
