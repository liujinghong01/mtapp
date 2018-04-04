package com.mt.lll.iv.warehouse.controller;

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
public class ApplyMatBillControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"lll/ApplyMatBill.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }

    /**
     * E390 查询领料单列表
     */
    @Test
    public void selectApplyMatListTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * B391 查询领料单详情
     */
    @Test
    public void selectApplyMatDetailTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(1);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E393 领料单新增修改
     */
    @Test
    public void applyMatAddAndModifyTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(2);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E393 领料单删除
     */
    @Test
    public void updateApplyMatModify() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(3);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
