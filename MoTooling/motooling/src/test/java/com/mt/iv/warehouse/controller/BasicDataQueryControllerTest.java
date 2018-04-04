package com.mt.iv.warehouse.controller;

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
public class BasicDataQueryControllerTest {


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    /**
     * E346 查询仓库列表
     * @throws Exception
     */
    @Test
    public void selectIvStorehouseListTest() throws Exception {
        String requestBody = "{\"company_id\":35,\"uid\":20,\"curr_page\":1,\"page_size\":2,\"token\":\"3d318e1a-355a-4431-9603-7e2daf75e808\",\"is_paging\":1}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/storehouse/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E347 查询仓库详细信息
     * @throws Exception
     */
    @Test
    public void selectIvStorehouseDetailTest() throws Exception {
        String requestBody = "{\"query\":{\"store_house_id\":\"1\"},\"token\":\"3d318e1a-355a-4431-9603-7e2daf75e808\",\"uid\":\"19\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/storehouse/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E359 查询入库类型列表
     * @throws Exception
     */
    @Test
    public void selectInStoreTypeListTest() throws Exception {
        String requestBody = "{\"company_id\":35,\"token\":\"3d318e1a-355a-4431-9603-7e2daf75e808\",\"uid\":\"19\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/in_store_type/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E362 查询出库类型列表
     * @throws Exception
     */
    @Test
    public void selectOutStoreTypeListTest() throws Exception {
        String requestBody = "{\"company_id\":35,\"token\":\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\",\"uid\":\"19\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/out_store_type/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E390 查询领料单列表
     */
    @Test
    public void selectApplyMatListTest() throws Exception {
        String requestBody = "{\"curr_page\":1,\"page_size\":20,\"query\":{\"approve_step\":\"\"},\"client_type\":2,\"uid\":19" +
                ",\"company_id\":35,\"token\":\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * B391 查询领料单详情
     */
    @Test
    public void selectApplyMatDetailTest() throws Exception {
        String requestBody = "{\"client_type\":\"\",\"company_id\":\"35\",\"query\":{\"apply_id\":\"1\"},\"token\":" +
                "\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\",\"uid\":\"1\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E393 领料单新增修改
     */
    @Test
    public void applyMatAddAndModifyTest() throws Exception {
        String requestBody = "{\"company_id\":\"35\",\"uid\":\"2\",\"client_type\":\"\",\"token\"" +
                ":\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\",\"apply_mat_info\":{\"apply_id\":1," +
                "\"apply_no\":\"20171128001\",\"approve_step\":\"测试内容3434\",\"approve_sugg\":" +
                "\"测试内容3434\",\"dep_id\":1,\"dep_name\":\"设计部\",\"request_date\":\"2017-09-28\"" +
                ",\"apply_detail_list\":[{\"detail_id\":1,\"curr_stock_qty\":1,\"deal_flag\":\"2\"" +
                ",\"mat_desc\":\"asdasss\",\"mat_id\":28876,\"mat_model\":\"测试内容5633\",\"mat_no\"" +
                ":\"测试内容4a35\",\"mat_type_id\":41348,\"mat_type_name\"" + ":\"测试内容xo8i\"," +
                "\"quantity\":86541,\"weight\":74350,\"remark\":\"asdsad\",\"unit\":\"assa\"}]}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/apply_mat_bill/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E424 查询盘点单列表
     */
    @Test
    public void selectStoreCheckListTest() throws Exception {
        String requestBody = "{\"curr_page\":1,\"page_size\":20,\"query\":{\"approve_step\":\"\"},\"client_type\":2,\"uid\":19" +
                ",\"company_id\":35,\"token\":\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E425 查询盘点单详情
     */

    @Test
    public void selectStoreCheckBillDetailTest() throws Exception {
        String requestBody = "{\"query\":{\"check_bill_id\":\"8\"},\"client_type\":2" +
                ",\"uid\":19,\"token\":\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E435 盘点单新增
     */
    @Test
    public void storeCheckBillAddTest() throws Exception {
        String requestBody = "{\"company_id\":\"15\",\"company_name\":\"asd\"" +
                ",\"uid\":\"2\",\"client_type\":\"\",\"token\":" +
                "\"5967c517-7c9c-44b1-8a98-b48d6358346c\",\"check_info\":" +
                "{\"operator_id\":\"1\",\"operator_name\":\"aaa\"," +
                "\"store_house_id\":\"11\",\"store_house_name\":\"dsn8\"" + "," +
                "\"mat_type_list\":[{\"mat_type_id\":41348,\"mat_type_name\":" +
                "\"测试内容xo8i\"}]}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E426 盘点单修改
     */
    @Test
    public void storeCheckBillModifyTest() throws Exception {
        String requestBody = "{\"company_id\":\"15\",\"uid\":\"2\",\"client_type\":\"\",\"token\":\"bb0bfe96-b648-426d-8fd7-fb42e917d438\"" +
                ",\"check_info\":{\"id\":\"1\",\"mat_id\":\"1\",\"mat_desc\":\"sdf\"}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/store_check_bill/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E418 查询报废单列表
     */
    @Test
    public void selectScrapMatListTest() throws Exception {
        String requestBody = "{\"curr_page\":1,\"page_size\":20,\"query\":{\"approve_step\":\"\"}" +
                ",\"client_type\":2,\"uid\":19,\"company_id\":35,\"token\":\"bb0bfe96-b648-426d-8fd7-fb42e917d438\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/scrap_mat_bill/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E419 查询报废单详情
     */

    @Test
    public void selectScrapMatDetailTest() throws Exception {
        String requestBody = "{\"query\":{\"scrap_id\":\"1\"},\"client_type\":2" +
                ",\"uid\":19,\"token\":\"bb0bfe96-b648-426d-8fd7-fb42e917d438\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/scrap_mat_bill/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E421 报废单新增修改
     */

    @Test
    public void scrapMatAddAndModifyTest() throws Exception {
        String requestBody =  "{\"client_type\":\"\",\"company_id\":\"35\"," +
                "\"uid\":\"2\",\"token\":\"5967c517-7c9c-44b1-8a98-b48d6358346c\"" +
                ",\"scrap_mat_info\":{\"scrap_id\":\"1\",\"approve_step\":\"step15\"" +
                ",\"approve_sugg\":\"测试内容3434s\",\"dep_id\":\"1\",\"dep_name\":\"设计部\"" +
                ",\"request_date\":\"2017-08-22\",\"scrap_no\":\"001\",\"scrap_detail_list\"" +
                ":[{\"detail_id\":\"1\",\"deal_flag\":\"2\",\"mat_desc\":\"sdsd\",\"mat_id\"" +
                ":28876,\"mat_model\":\"测试内容5633\",\"mat_no\":\"测试内容4a35\",\"mat_type_id\"" +
                ":41348,\"mat_type_name\":\"测试内容xo8i\",\"quantity\":86541,\"weight\":74350," +
                "\"remark\":\"asdsad\",\"unit\":\"assa\",\"store_house_id\":\"1\"," +
                "\"store_house_name\":\"仓库1\"" + ",\"store_room_id\":\"1\",\"store_room_name\":\"库位1\"}]}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/scrap_mat_bill/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E434 执行报废
     */

    @Test
    public void ExecuteScrapTest() throws Exception {
        String requestBody =  "{\"uid\":\"19\",\"client_type\":\"2\",\"company_id\":\"35\"" +
                ",\"scrap_id\":\"1\",\"mat_id\":\"1\",\"token\":\"bb0bfe96-b648-426d-8fd7-fb42e917d438\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/iv/scrap_mat_bill/scrap")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
