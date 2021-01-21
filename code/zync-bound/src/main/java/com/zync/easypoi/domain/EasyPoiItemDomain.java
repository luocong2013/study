package com.zync.easypoi.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 01396453(luocong)
 * @version 3.0.9
 * @description
 * @date 2021/1/21 16:50
 */
@Data
@AllArgsConstructor
public class EasyPoiItemDomain {

    @Excel(name = "订单号/箱号", width = 20)
    private String orderBoxNumber;
}
