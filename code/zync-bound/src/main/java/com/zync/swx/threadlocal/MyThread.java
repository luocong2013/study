package com.zync.swx.threadlocal;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.threadlocal
 * @description TODO
 * @date 2017-10-25 13:45
 */
public class MyThread implements Runnable {

    public static void main(String[] args){
      MyThread thread = new MyThread();

      Thread t1 = new Thread(thread);
      Thread t2 = new Thread(thread);
      Thread t3 = new Thread(thread);

      t1.start();
      t2.start();
      t3.start();
    }

    @Override
    public void run() {
        new DateUtils().getNowDateTime();
    }
}
