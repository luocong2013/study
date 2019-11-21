package com.zync.aio;

import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试
 * @date 2019/11/21 22:33
 */
public class ServerClient {

    public static void main(String[] args) throws Exception {
        new AIOEchoServer().start();
        while (true) {
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
