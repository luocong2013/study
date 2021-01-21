package com.zync.easypoi.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 01396453(luocong)
 * @version 3.0.9
 * @description
 * @date 2021/1/21 16:49
 */
@Data
@AllArgsConstructor
public class DogDomain {

    @Excel(name = "狗的名字")
    private String name;

    @Excel(name = "狗的年龄")
    private int age;
}
