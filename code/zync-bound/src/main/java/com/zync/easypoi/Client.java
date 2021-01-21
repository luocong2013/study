package com.zync.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.common.collect.Lists;
import com.zync.easypoi.domain.DogDomain;
import com.zync.easypoi.domain.EasyPoiDomain;
import com.zync.easypoi.domain.EasyPoiItemDomain;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 01396453(luocong)
 * @version 3.0.9
 * @description easypoi测试
 * @date 2021/1/21 17:00
 */
public class Client {

    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();

        // 查询内容
        Map<String, Object> contentMap = new HashMap<>(4);
        ExportParams contentParams = new ExportParams("查询内容", "查询内容");
        contentMap.put("title", contentParams);
        contentMap.put("entity", EasyPoiDomain.class);
        List<EasyPoiDomain> pois = Lists.newArrayList();
        EasyPoiDomain poi = new EasyPoiDomain(
                "yuab1",
                new Date(),
                BigDecimal.valueOf(2897931.98371),
                Lists.newArrayList(
                        new EasyPoiItemDomain("L001"),
                        new EasyPoiItemDomain("L002"),
                        new EasyPoiItemDomain("L003"))
        );
        pois.add(poi);
        contentMap.put("data", pois);

        list.add(contentMap);

        Map<String, Object> dogMap = new HashMap<>(4);
        ExportParams dogParams = new ExportParams("清单二", "狗狗清单");
        dogMap.put("title", dogParams);
        dogMap.put("entity", DogDomain.class);
        List<DogDomain> dogs = Lists.newArrayList();
        dogs.add(new DogDomain("泰迪", 3));
        dogs.add(new DogDomain("哈士奇", 6));
        dogMap.put("data", dogs);

        list.add(dogMap);

        Workbook sheets = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);

        try (OutputStream stream = new FileOutputStream("d:/test.xls")) {
            sheets.write(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
