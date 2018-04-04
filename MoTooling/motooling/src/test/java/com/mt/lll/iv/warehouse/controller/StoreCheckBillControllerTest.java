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
public class StoreCheckBillControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"lll/StoreCheckBill.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }

    /**
     *  E424 查询盘点单列表
     */
    @Test
    public void selectStoreCheckList() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *  E425 查询盘点单待盘已盘物料
     */
    @Test
    public void selectStoreCheckBillDetail() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(1);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *  E435 盘点单生成
     */
    @Test
    public void storeCheckBillAdd() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(2);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *  E426 盘点单物料盘点
     */
    @Test
    public void storeCheckBillModify() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(3);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

}
