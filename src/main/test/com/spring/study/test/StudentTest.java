package com.spring.study.test;

import com.spring.study.config.WebConfig;
import com.spring.study.controller.TeacherController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2016/11/5.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TeacherController.class})
@WebAppConfiguration
public class StudentTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Before
    public void init()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testSign() throws Exception {
        mockMvc.perform(post("/teacher_login")
                .param("username","111111")
                .param("password","111111"))
                //.andDo(print())
                .andExpect(view().name("index"));
    }
}
