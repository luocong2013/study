package com.zync.json;

import com.zync.json.domain.JsonDomain;

import java.sql.Time;
import java.util.Date;

/**
 * @author luocong
 * @description 测试
 * @date 2020/5/25 10:02
 */
public class JsonClient {

    public static void main(String[] args) {
        JsonDomain domain = JsonDomain.builder().id(1).name("张三").address("成都市双流区").ipAddr("192.168.0.100").birthday(new Date()).createTime(new Time(System.currentTimeMillis())).build();

        // FastJson
        System.out.println("======================FastJson=======================");
        String fastJsonText = FastJsonUtil.bean2Json(domain);
        System.out.println(fastJsonText);
        JsonDomain jsonDomainFastJson = FastJsonUtil.json2Bean(fastJsonText, JsonDomain.class);
        System.out.println(jsonDomainFastJson);
        System.out.println("======================FastJson=======================\n");


        // Gson
        System.out.println("======================Gson=======================");
        String gsonText = GsonUtil.bean2Json(domain);
        System.out.println(gsonText);
        JsonDomain jsonDomainGson = GsonUtil.json2Bean(gsonText, JsonDomain.class);
        System.out.println(jsonDomainGson);
        System.out.println("======================Gson=======================\n");

        // Jackson
        System.out.println("======================Jackson=======================");
        String jacksonText = JacksonUtil.toJson(domain);
        System.out.println(jacksonText);
        JsonDomain jsonDomainJackson = JacksonUtil.toBean(jacksonText, JsonDomain.class);
        System.out.println(jsonDomainJackson);
        System.out.println("======================Jackson=======================\n");
    }
}
