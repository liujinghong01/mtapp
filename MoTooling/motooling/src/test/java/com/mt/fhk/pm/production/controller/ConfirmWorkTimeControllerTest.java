package com.mt.fhk.pm.production.controller;


import com.mockito.util.FileUtil;
import net.sf.json.JSONArray;
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
 * @Author: Fhk
 * @Description:
 * @create    2018/1/22 13:10
 * @Modified By:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({@ContextConfiguration(name = "parent",locations = "classpath:spring.xml"),
        @ContextConfiguration(name = "child",locations = "classpath:spring-mvc.xml")})
public class ConfirmWorkTimeControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"fhk/PmConfirmWorkTime.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }


    @Test
    public void queryPgProcInfoTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/proc_info")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    @Test
    public void queryWaitProcListTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(1);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/wait_proc_list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    @Test
    public void popInceptTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(2);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/incept")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    @Test
    public void popAllocatResourceTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(3);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/allocate")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    @Test
    public  void popChangeStatusTest1() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(4);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/change_status")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    @Test
    public  void popChangeStatusTest2() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(5);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/change_status")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    @Test
    public void popChangeWorkerTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(6);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pop/change_worker")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
