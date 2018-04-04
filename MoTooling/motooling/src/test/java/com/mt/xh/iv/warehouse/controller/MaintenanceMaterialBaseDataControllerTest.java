package com.mt.xh.iv.warehouse.controller;


import com.mockito.util.FileUtil;
import com.mt.order.common.dao.ComputeMapper;
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
@ContextHierarchy({@ContextConfiguration(name = "parent", locations = "classpath:spring.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml")})
public class MaintenanceMaterialBaseDataControllerTest {


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/") + "xh/Mainteance.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }

    /**
     * E432 库存物料数据筛选（按仓库分组统计）
     * @throws Exception
     */
    @Test
    public void selectSearchMatTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_house/search_mat")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E456 库存物料数据筛选（按仓库库位分组统计）
     * @throws Exception
     */
    @Test
    public void selectRommSearchMatTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(1);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_room/search_mat")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E433 库存物料数据筛选（按全库存统计）
     * @throws Exception
     */
    @Test
    public void selectStoreSearchMatTest() throws Exception {
        JSONObject jsonObject = jsonArray.getJSONObject(2);
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store/search_mat")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

}
