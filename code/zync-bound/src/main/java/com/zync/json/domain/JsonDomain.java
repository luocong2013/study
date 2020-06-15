package com.zync.json.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

/**
 * @author luocong
 * @description 测试json
 * @date 2020/5/25 9:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonDomain {

    private Integer id;

    private String name;

    private String address;

    private String ipAddr;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @JSONField(format = "HH:mm:ss")
    private Time createTime;

    private String idCard;
}
