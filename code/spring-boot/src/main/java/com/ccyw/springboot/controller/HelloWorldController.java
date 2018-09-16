package com.ccyw.springboot.controller;

import com.ccyw.springboot.model.ConfigModel;
import com.ccyw.springboot.properties.MyWebProperties;
import com.ccyw.springboot.properties.CcywProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 */
@RestController
public class HelloWorldController {

    /**
     * 读取application配置文件中的数据 方式一
     */
    @Value("${helloworld}")
    private String helloworld;

    /**
     * 读取application配置文件中的数据 方式二 (防止乱码统一编码格式)
     */
    @Autowired
    private Environment env;

    /**
     * 读取application配置文件中的数据 方式三
     */
    @Autowired
    private CcywProperties ccywProperties;

    /**
     * 读取自定义配置文件数据
     */
    @Autowired
    private MyWebProperties myWebProperties;

    /**
     * 读取自定义配置文件数据
     */
    @Autowired
    private ConfigModel configModel;

    @GetMapping({"/hello", "/hi"})
    public String hello() {
        return helloworld;
    }

    @GetMapping(value = "/spack")
    public String spack() {
        return env.getProperty("spackchinese");
    }

    @GetMapping(value = "/ccyw")
    public String ccyw() {
        return ccywProperties.getAddress() + ": " + ccywProperties.getPhone() + ": " + ccywProperties.getAge();
    }

    @GetMapping(value = "/web")
    public String web() {
        return myWebProperties.getTitle() + " " + myWebProperties.getAuthor() + " " + myWebProperties.getVersion();
    }

    @GetMapping(value = "/config")
    public ConfigModel config() {
        return configModel;
    }
}
