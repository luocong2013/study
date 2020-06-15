package com.zync.swx.hutool;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.hutool
 * @description TODO
 * @date 2018-1-8 16:39
 */
public class NoVisibility implements Runnable {
    private boolean ready;
    private int number;

    public static void main(String[] args){
       NoVisibility visibility = new NoVisibility();
       visibility.number = 42;
       visibility.ready = true;
       new Thread(visibility).start();
    }

    @Override
    public void run() {
        while (!ready) {
            Thread.yield();
        }
        System.out.println(number);
    }
}
