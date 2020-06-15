package com.zync.swx.threadlocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.threadlocal
 * @description TODO
 * @date 2017-10-25 13:45
 */
public class DateUtils {

    private ThreadLocal<DateVO> threadLocal = new ThreadLocal<DateVO>() {
        @Override
        protected DateVO initialValue() {
            return new DateVO(Thread.currentThread().getId(), Thread.currentThread().getName());
        }
    };



    public void getNowDateTime() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateVO dateVO = threadLocal.get();
        DateVO.COUNT++;
        System.out.println("id:"+dateVO.getId() + " name:"+dateVO.getName() + " count:" + DateVO.COUNT);
        System.out.println(format.format(new Date()));
    }
}
