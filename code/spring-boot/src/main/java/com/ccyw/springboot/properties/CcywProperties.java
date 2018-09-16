package com.ccyw.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author luoc
 * @version V1.0.0
 * @description application配置文件类
 * @date 2018/6/28 22:11
 */
@Component
@ConfigurationProperties(prefix = "ccyw")
public class CcywProperties {
    private String address;
    private String phone;
    private Integer age;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
