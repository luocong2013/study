package com.zync.swx.executor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-10-27 16:08
 */
public class DateUtil {

    public static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
