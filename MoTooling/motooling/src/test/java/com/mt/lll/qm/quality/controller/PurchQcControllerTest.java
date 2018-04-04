package com.mt.lll.qm.quality.controller;

import com.mockito.util.FileUtil;
import net.sf.json.JSONArray;
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
* @Author: Wendy
* @Description:
* @create    2018/1/22 15:06
* @Modified By:
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({@ContextConfiguration(name = "parent",locations = "classpath:spring.xml"),
        @ContextConfiguration(name = "child",locations = "classpath:spring-mvc.xml")})
public class PurchQcControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    /**
     * E788 查询来料质检列表
     */
    @Test
    public void selectPurchQcList() throws Exception {
        String  requestBody=
                "{\"curr_page\":1," +
                "\"page_size\":20," +
                "\"query\":" +
                        "{\"approve_step\":[\"step20\"]}," +
                        "\"client_type\":2," +
                        "\"uid\":19," +
                        "\"company_id\":35," +
                        "\"token\":\"8e54e0e7-533a-440e-81b6-3d8bed180c62\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *E789 查询来料质检详情
     */
    @Test
    public void selectPurchQcDetail() throws Exception {
        String  requestBody="{\"client_type\"" +
                ":\"\",\"company_id\"" +
                ":\"35\",\"query\"" +
                ":{\"purch_qc_no\":\"PQ18010004\"}" +
                ",\"token\":\"889b51bc-0fa9-4462-bb0f-dc0be73e3a48\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/qm/purch_qc/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *E846 查询待质检来料详情
     */
    @Test
    public void selectStayCheckPurchDetail() throws Exception {
        String  requestBody= "{\"client_type\"" +
                ":\"\",\"company_id\"" +
                ":\"35\",\"query\"" +
                ":{\"purch_qc_no\":\"PQ18010004\"}" +
                ",\"token\":\"889b51bc-0fa9-4462-bb0f-dc0be73e3a48\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/qm/purch_qc_stay/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E791 新增修改来料质检单
     */
    @Test
    public void addAndUpdatePurchQcBill() throws Exception {
        String  requestBody= "{\"client_type\":\"\",\"company_id\":" +
                "\"35\",\"company_name\":\"联大\",\"token\":\"0c4e2193-c9f3-4b74-90" +
                "99-e4e0dfdf3468\",\"uid\":\"19\",\"purch_qc_info\":{\"approve_sugg\"" +
                ":\"aaa\",\"sup_name\":\"联华\",\"qc_date\":\"2018-01-12\",\"purch_" +
                "qc_no\":\"PQ18010004\",\"purch_type\":\"1\",\"qc_detail_list\":[{\"" +
                "purch_id\":1,\"purch_no\":\"MP000001\",\"purch_detail_list\":[{\"qc" +
                "_bill_qty\":40,\"quantity\":100,\"mat_desc\":\"psi\",\"purch_sub_id" +
                "\":1,\"no_qc_qty\":60,\"mat_no\":\"HJ001\",\"mat_type_id\":\"1\",\"" +
                "unusual_reason\":\"质量没问题\",\"mat_model\":null,\"no_qualified_qty" +
                "\":0,\"special_qty\":20,\"mat_id\":\"1\",\"purch_qc_detail_id\":85," +
                "\"is_qc\":\"1\",\"qc_qty\":50,\"qualified_qty\":10,\"mat_type_name\"" +
                ":\"1\"},{\"qc_bill_qty\":70,\"quantity\":100,\"mat_desc\":\"asi\",\"p" +
                "urch_sub_id\":10,\"no_qc_qty\":30,\"mat_no\":\"HJ002\",\"mat_type_id\"" +
                ":\"2\",\"unusual_reason\":\"质量没问题\",\"mat_model\":null,\"no_qua" +
                "lified_qty\":0,\"special_qty\":20,\"mat_id\":\"1\",\"purch_qc_detail" +
                "_id\":86,\"is_qc\":\"1\",\"qc_qty\":10,\"qualified_qty\":10,\"" +
                "mat_type_name\":\"1\"}]," + "\"is_qc\":\"1\"}],\"approve_step\":" +
                "\"step10\",\"inspector\":\"lll\",\"purch_qc_id\":15,\"sup_company_id\":1}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/qm/purch_qc_bill/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E790 来料质检单删除
     */
    @Test
    public void updatePurchQcBill() throws Exception {
        String  requestBody=
                "{\"client_type\":" +
                "\"\",\"purch_qc_id\":" +
                "\"\",\"token\"" + ":\"a6c1bee7-da50-4746-9961-5324eb5d59f8\"" +
                ",\"uid\":\"1\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/qm/purch_qc_bill/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
