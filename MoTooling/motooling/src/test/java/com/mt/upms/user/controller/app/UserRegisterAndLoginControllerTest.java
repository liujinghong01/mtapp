package com.mt.upms.user.controller.app;

import net.sf.json.JSONObject;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by lrd on 2017/9/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:"})
@ContextHierarchy({@ContextConfiguration(name = "parent",locations = "classpath:spring.xml"),
        @ContextConfiguration(name = "child",locations = "classpath:spring-mvc.xml")})
public class UserRegisterAndLoginControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testLogin()throws Exception{
        String requestBody = "{\"identity_type\":\"email\",\"password\":\"123 456\",\"user\":\"randy@qq.com\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        JSONObject jsonObject = JSONObject.fromObject(mvcResult.getResponse().getContentAsString());
        String uid = jsonObject.getJSONObject("data").getString("uid");
        String token = jsonObject.getJSONObject("data").getString("token");

        String requestBody2 = "{\"client_type\":\"2\",\"search_type\":\"1\",\"company_id\":\"35\",\"com_user_name\":\"深圳\",\"uid\":\""+uid+"\",\"token\":\""+token+"\"}";
        MvcResult mvcSearchResult = mockMvc.perform(post("/api/com-user/search")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody2)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
       // Assert.assertNull(mvcResult.getModelAndView());
    }

}
