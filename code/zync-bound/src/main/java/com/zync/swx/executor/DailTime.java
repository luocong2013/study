package com.zync.swx.executor;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-10-27 14:27
 */
public class DailTime {
    public static void main(String[] args) {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("example-schedule-poop-%d").daemon(false).build());

        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = DateUtil.getTimeMillis("17:09:00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;

        service.scheduleWithFixedDelay(new TaskRunnable(), initDelay, oneDay, TimeUnit.MILLISECONDS);

        //6000天后结束线程
        service.schedule(() -> service.shutdown(), 6000, TimeUnit.DAYS);
    }
}
