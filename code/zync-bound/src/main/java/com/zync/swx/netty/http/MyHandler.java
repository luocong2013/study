package com.zync.swx.netty.http;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-12-13 12:31
 */
public class MyHandler implements HttpHandler {

    boolean isFinish = false;
    String id;

    public MyHandler(String id) {
        this.id = id;
    }

    @Override
    public void handler(Map<String, String> headMap, String body) {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getId() + "自己处理：" + body);
        this.setIsFinish(true);
    }

    @Override
    public Charset getCharset() {
        return Charset.forName("UTF-8");
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean finish) {
        isFinish = finish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
