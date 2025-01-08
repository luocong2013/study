package com.zync.r2dbc.springdata.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * 用户类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2025/1/8 16:35
 */
@Table("user")
@Getter
@Setter
@ToString
public class User {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Instant createTime;

    private Instant updateTime;

    private String deleted;
}
