package com.mt.lll.pm.devicemanager.controller;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({@ContextConfiguration(name = "parent",locations = "classpath:spring.xml"),
        @ContextConfiguration(name = "child",locations = "classpath:spring-mvc.xml")})
public class ProductionControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"lll/PmPgProduction.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }

    /**
     * E505 查询生产小组列表
     */
    @Test
    public void selectProductionGroupList() throws Exception {
        String requestBody = "{\"curr_page\":1," +
                "\"page_size\":20," +
                "\"query\":{\"dep_id\":\"\"}" + "" +
                ",\"client_type\":2" +
                ",\"uid\":19," +
                "\"company_id\":35," +
                "\"token\":\"f68996e8-4f76-4e96-b179-d7107553f930\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E698 生产小组删除
     */

    @Test
    public void updateProductionGroupModify() throws Exception {
        String requestBody = "{\"client_type\":\"\"" +
                ",\"company_id\":\"35\"" +
                ",\"pg_id\":\"1\"" +
                ",\"token\":\"a6c1bee7-da50-4746-9961-5324eb5d59f8\"" +
                ",\"uid\":\"1\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E505 查询生产小组详情
     *
     *
     */
    @Test
    public void selectProductionGroupDetail() throws Exception {
        String requestBody = "{\"client_type\":" +
                ",\"company_id\":\"35\"" +
                "," + "\"query\":" +
                "{\"pg_id\":\"1\"}" +
                ",\"token\":\"81a7128b-2de0-486a-b566-7ab5b951ae70\"" +
                ",\"uid\":\"1\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E699 生产小组新增修改
     */

    @Test
    public void addAndUpdateProductionGroup() throws Exception {
        String  requestBody="{\"client_type\":\"\"" +
                ",\"company_id\":\"35\"" +
                ",\"uid\":\"71\"" +
                ",\"token\":\"3199da5c-1f01-41b8-a7d2-7e7d7fe334d3\"" +
                ",\"member_info\":" +
                "{\"member_id\":\"71\"" +
                ",\"furlough_end_date\":" +
                "\"2017-12-25\"," +
                "\"furlough_start_date\":" +
                "\"2017-12-21\"," +
                "\"is_leader\":\"0\"" +
                ",\"pg_id\":\"3\"," +
                "\"position_code\":\"4\"" +
                ",\"position_name\":"
                + "\"开发\",\"work_email\"" +
                ":\"135874121@qq.coms\"," +
                "\"work_name\":\"lll\"," +
                "\"work_phone\":\"135978456\"," +
                "\"work_state\":\"1\"}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
