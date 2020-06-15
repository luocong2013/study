package com.zync.swx.hutool;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.hutool
 * @description TODO
 * @date 2018-1-4 17:13
 */
public class TaskRunnable implements Runnable {

    private DataVO dataVO;

    public TaskRunnable(DataVO dataVO) {
        this.dataVO = dataVO;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 现在的Value值：" + dataVO.getValue());
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
