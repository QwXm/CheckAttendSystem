package com.spring.study.test;

import com.spring.study.config.AppConfig;
import com.spring.study.config.WebConfig;
import com.spring.study.controller.ControllerConfig;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2016/10/30.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WebConfig.class, ControllerConfig.class})
@WebAppConfiguration
public class SignTest {
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
        mockMvc.perform(post("/student_sign")
                .param("studentNumber","111111"))
                //.andDo(print())
                .andExpect(view().name("index"));

    }
}
