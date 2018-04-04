package com.mt.cms.company.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * Created by lrd on 2017/9/29.
 */
//xml风格
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:"})
@ContextHierarchy({@ContextConfiguration(name = "parent",locations = "classpath:spring.xml"),
        @ContextConfiguration(name = "child",locations = "classpath:spring-mvc.xml")})
//注解风格
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration(value = "src/main/webapp")
//@ContextHierarchy({
//        @ContextConfiguration(name = "parent", classes = AppConfig.class),
//        @ContextConfiguration(name = "child", classes = MvcConfig.class)
//})
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testEnterpriseInformation()throws Exception{
        //JSON请求/响应
        String requestBody = "{\"client_type\":\"2\",\"search_type\":\"1\",\"com_user_name\":\"测试\",\" uid\":\"30\",\"token\":\"63f32602-9ed0-4d38-b35a-c9e7055328bd\"}";
        MvcResult result = mockMvc.perform(post("/api/cst-sup/search")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(result.getModelAndView());
    }




    //@Test
    /*public void searchControllerTest(){
        List<String> list = mock(List.class);
        when(list.get(0)).thenReturn("helloworld");
        when(list.get(1)).thenReturn(String.valueOf(new RuntimeException("test exception")));
        String result = list.get(0);
        verify(list).get(0);
        Assert.assertEquals("helloworld", result);
    }*/

}


