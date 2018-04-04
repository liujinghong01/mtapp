package com.mt.wj.pm.production.controller;

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

import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-17 13:47
 * @Description:
 * @Modified By:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({@ContextConfiguration(name = "parent",locations = "classpath:spring.xml"),
                   @ContextConfiguration(name = "child",locations = "classpath:spring-mvc.xml")})
public class CalendarControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"wj/PmCalendar.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }

    /**
     * E683 查询日历列表
     * @throws Exception
     */
    @Test
    public void selectPmCalendarListTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/calendar/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E739 查询日历详情
     * @throws Exception
     */
    @Test
    public void selectPmCalendarDetailTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(1);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/calendar/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E686 新增/修改日历
     * @throws Exception
     */
    @Test
    public void addAndUpdatePmCalendarTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(2);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/calendar/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E685 删除日历
     * @throws Exception
     */
    @Test
    public void deletePmCalendarTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(3);
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/calendar/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
