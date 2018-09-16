package com.ccyw.springboot.own.jdbc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author luoc
 * @version V1.0.0
 * @description 测试
 * @date 2018/8/19 15:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnControllerTest {

    @Autowired
    private LearnController learnController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(learnController).build();
    }

    @Test
    public void getLearnList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/jdbc/learns").accept(MediaType.APPLICATION_JSON)
                /*.param("id", "1")*/)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("输出：" + mvcResult.getResponse().getContentAsString());
    }
}