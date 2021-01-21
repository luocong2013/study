package com.zync.easypoi.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 01396453(luocong)
 * @version 3.0.9
 * @description
 * @date 2021/1/21 16:50
 */
@Data
@AllArgsConstructor
public class EasyPoiDomain {

    @Excel(name = "查询内容", needMerge = true)
    private String queryContent;

    @Excel(name = "查询时间", exportFormat = "yyyy-MM-dd HH:mm:ss", width = 20, needMerge = true)
    private Date createTime;

    @Excel(name = "金额", numFormat = ",###.##", width = 15, needMerge = true)
    private BigDecimal money;

    @ExcelCollection(name = "")
    private List<EasyPoiItemDomain> orderBoxNumbers;
}
