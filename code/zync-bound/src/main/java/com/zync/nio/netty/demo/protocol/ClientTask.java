package com.zync.nio.netty.demo.protocol;

/**
 * 说明：
 *
 * @author luoc 2015年11月5日
 */
public class ClientTask implements Runnable {

    /**
     *
     */
    public ClientTask() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            ProtocolClient client = new ProtocolClient("localhost", 8082);

            client.run();


        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
