package com.ccyw.springboot.own.jdbc.controller;

import com.ccyw.springboot.own.common.bean.Learn;
import com.ccyw.springboot.own.jdbc.service.LearnServiceJdbc;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description Learn 控制器层
 * @date 2018/8/14 22:55
 */
@RestController
@RequestMapping(value = "/jdbc")
public class LearnController {

    @Autowired
    private LearnServiceJdbc learnServiceJdbc;

    @ApiOperation(value = "获取学习数据列表", notes = "")
    @GetMapping(value = "/learns")
    public List<Learn> getLearnList() {
        return learnServiceJdbc.getLearns();
    }

    @ApiOperation(value = "保存数据", notes = "根据Learn对象创建数据")
    @ApiImplicitParam(name = "learn", value = "学习详情实体learn", required = true, dataType = "Learn")
    @PostMapping(value = "/save")
    public String postLearn(@RequestBody Learn learn) {
        learnServiceJdbc.save(learn);
        return "保存成功";
    }
}
