package com.zync.swx.comparator;

import java.io.Serializable;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.comparator
 * @description TODO
 * @date 2017-10-31 17:02
 */
public class User implements Serializable {

    private Integer id;
    private String name;
    private String passwd;
    private Integer age;

    public User() {
    }

    public User(Integer id, String name, String passwd, Integer age) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
