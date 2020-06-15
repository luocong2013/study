package com.zync.swx.executor;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-10-27 14:54
 */
public class TaskRunnable implements Runnable {
    @Override
    public void run() {
        HttpClientQuery query = new HttpClientQuery();
        System.out.println(query.sendGet("http://192.168.2.16:8080/", "UTF-8"));
    }
}
