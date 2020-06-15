package com.zync.example.springbootdemo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 初始化加载数据-定时任务
 * @date 2020/5/22 10:09
 */
@Component
public class LoadDataSchedule {

    /**
     * fixedDelay：业务代码运行完成后再延迟的时间（固定延迟）
     * fixedRate：两次执行之间的间隔（固定速度）
     */
    @Scheduled(fixedDelay = 1000)
    public void loadData() {
        System.out.println("loadData1: " + Thread.currentThread().getName() + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "* * * * * ?")
    public void loadData2() {
        System.out.println("loadData2: " + Thread.currentThread().getName() + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
