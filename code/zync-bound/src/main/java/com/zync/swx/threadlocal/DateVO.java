package com.zync.swx.threadlocal;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.threadlocal
 * @description TODO
 * @date 2017-10-25 14:16
 */
public class DateVO {
    private long id;
    private String name;

    public static int COUNT = 0;

    public DateVO() {
    }

    public DateVO(long id) {
        this.id = id;
    }

    public DateVO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
