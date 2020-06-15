package com.zync.swx.netty.http;

import java.util.concurrent.TimeUnit;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-12-13 12:39
 */
public class NonBlockHttpClientTest {

    public static void main(String[] args){
        //MyHandler myHandler = new MyHandler("A");
        //MyHandler myHandler1 = new MyHandler("B");
        //MyHandler myHandler2 = new MyHandler("C");
        MyHandler myHandler3 = new MyHandler("D");
        //NonBlockHttpClient.get("http://192.168.1.228:8081/zentao/user-login-L3plbnRhby9teS5odG1s.html", null, myHandler);
        //NonBlockHttpClient.get("http://192.168.2.24:8081/secure/Dashboard.jspa", null, myHandler1);
        //NonBlockHttpClient.get("http://192.168.2.16:8080/", null, myHandler2);
        NonBlockHttpClient.get("http://192.168.2.16:8080/", null, myHandler3);
        System.out.println("做别的事情");

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!(/*myHandler.isFinish() && myHandler1.isFinish() && myHandler2.isFinish() && */myHandler3.isFinish())) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        NonBlockHttpClient.close();
        System.out.println("退出主函数……");
    }
}
