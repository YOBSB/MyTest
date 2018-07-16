package com.miniworld.controller;

import com.miniworld.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class TestContronllerTest extends BaseTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testEventID()throws Exception {
        mockMvc.perform(get("/20180602/test/eventID")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testSysConfig()throws Exception {
        mockMvc.perform(get("/test/sysConfig")).andExpect(status().isOk()).andDo(print());
    }
}
