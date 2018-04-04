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
public class DeviceControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private JSONArray jsonArray;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"lll/PmPgDevice.json");
        jsonArray = JSONArray.fromObject(JsonContext);
    }


    /**
     * E516 查询设备点检分配表
     */
    @Test
    public void selectDeviceCheckAllocate() throws Exception {
        String  reqestBody="{\"query\":" +
                "{\"check_uid\":\"2\"}," +
                "\"client_type\":\"\"" +
                ",\"uid\":19," +
                "\"company_id\":35," +
                "\"token\":\"f68996e8-4f76-4e96-b179-d7107553f930\"}\n" + "\n";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device_check_allocate/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(reqestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * 783 查询设备点检分配详情表
     */
    @Test
    public void selectDeviceCheckAllocateDetail() throws Exception {
        String  reqestBody="{\"query\":" +
                "" + "{\"allocate_id\":" + "\"10\"}," +
                "\"client_type\":" +
                "\"\",\"uid\"" + ":19," +
                "\"company_id\":" + "35," +
                "\"token\"" + ":\"81aa7dbf-c0d1-49af-abb0-50e541dad545\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device_check_allocate/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(reqestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E702 设备点检分配
     */
    @Test
    public void DeviceCheckAllocateManager() throws Exception {
        String  requestBody="{\"company_id\":" + "\"35\"," +
                "\"uid\":" + "\"19\"," +
                "\"client_type\"" + ":\"\"," +
                "\"token\":" +
                "\"7bea0221-f007-4713-b2ac-be0a88e7056b\"," +
                "\"check_allocate_info\":" +
                "" + "{\"allocate_id\":" + "\"4\"," +
                "\"pg_id\":" +
                "\"659\"," +
                "\"check_uid_list\":" +
                "[{\"check_uid\":\"5\"" + "," +
                "\"check_uid_name\":\"大白\"}]," +
                "\"device_list\":" +
                "[{\"device_id\":" + "\"4\"," +
                "\"device_name" + "\":" +
                "\"cesneir测试内容\"}," +
                "{\"device_id\":\"5\"," +
                "\"device_name\":\"cesneir测试内容\"}," +
                "" + "{\"device_id\":\"6\"" +
                ",\"device_name\":\"cesneir测试内容\"}]}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/check_allocate")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *E806 设备点检分配删除
     */
    public void  updateCheckAllocate()throws   Exception{
        String  requestBody="{\"client_type\":\"\"" +
                ",\"check_allocate_info\"" +
                ":{\"allocate_id\":\"\"}," +
                "\"token\":" +
                "\"a6c1bee7-da50-4746-9961-5324eb5d59f8\"" +
                ",\"uid\":\"1\"," +
                "\"company_id\":\"\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/check_allocate_delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     *E548 查询设备点检表
     */
    @Test
    public void selectDeviceCheckList() throws Exception {
        String  requestBody="{\"query\":" +
                "{\"check_uid\":\"1\"," +
                "\"device_id\":\"\"}," +
                "\"client_type\"" +
                ":\"\",\"uid\":19," +
                "\"company_id\":35," +
                "\"token\":\"fcfbca61-42ea-44b0-9949-23ff73da3997\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device_check/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E704 设备点检
     */
    @Test
    public void DeviceCheckManager() throws Exception {
        String  requestBody= "{\"company_id\":\"35\"" +
                ",\"uid\":\"19\"" +
                ",\"client_type\":" +
                "\"\",\"token\":" +
                "\"28d1b32b-c189-47ea-81b7-9ff6f7a1c65b\"," +
                "\"device_check_info\":" +
                "{\"device_id\":\"2\"" + "," +
                "\"is_checked\":\"1\"," +
                "\"remark\":\"该设备在今天点检时候发现问题\"}}\n" + "\n";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/check")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E649 查询设备列表
     */
    @Test
    public void selectDeviceList() throws Exception {
        String  requestBody= "{\"curr_page\":1," +
                "\"page_size\":20," +
                "\"query\":" +
                "{\"device_name\":" +
                "\"cesneir测试内容\"," +
                "\"pg_id\":\"2\"}," +
                "\"client_type\":2," +
                "\"uid\":19," +
                "\"company_id\":35," +
                " \"token\": \"6187136b-550d-4604-bc59-b44ef6667c4d\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/qm/purch_qc/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E703 设备检修开始
     */
    @Test
    public void startDeviceMaintainBill() throws Exception {
        String requestBody="{\"company_id\":\"35\"" +
                ",\"device_maintain_info\":" +
                "{\"device_id\":\"1\"},"
                + "\"client_type\":" +
                "\"\",\"uid\":\"19\"," +
                "\"token\":\"6b590cde-669c-4344-aca7-b961cda97d4d\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/device_maintain/start")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E766 查询设备当前检修信息
     */
    public   void  selectDeviceMaintainCurr() throws   Exception{
        String requestBody= "{\"query\":" +
                "{\"device_id\":\"2\"}," +
                "\"client_type\":\"\"," +
                "\"uid\":\"19\"," +
                "\"token\":\"11de589e-c12f-4182-991b-d9c5df99509e\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/device_maintain_curr/query")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * E764 设备检修单保存 (检修完毕)
     */
    public void endDeviceMaintainBill()throws  Exception{
        String  requestBody="{\"company_id\":\"35\"," +
                "\"client_type\":\"\"," +
                "\"uid\":\"19\"" + "," +
                "\"token\":\"11de589e-c12f-4182-991b-d9c5df99509e\"" + "" +
                ",\"device_maintain_info\":" +
                "{\"cycle\":\"1\"" +
                ",\"device_id\":\"2\"" +
                ",\"device_name\":\"设备\"" +
                ",\"end_date\":\"2017-12-27\"" + "" +
                ",\"length\":\"2\"" +
                "," + "\"maintain_no\":\"001\"" +
                ",\"next_end_date\""
                + ":\"2017-12-28\"" +
                "," + "\"next_start_date\"" +
                ":\"2017-12-29\"" + "" +
                ",\"reason\":\"as\"" +
                ",\"start_date\":\"2017-12-26\"}}";
        MvcResult  mvcResult=mockMvc.perform(post("/api/pm/device_maintain_bill/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());

    }

    /**
     * E513 查询设备检修记录
     */
    @Test
    public void selectDeviceMainTainRecord() throws Exception {
        String  requestBody="{\"curr_page\":1," +
                "\"page_size\":20" +
                ",\"query\":" +
                "{\"device_id\":\"6\"}" +
                "," + "\"client_type\":2" +
                ",\"uid\":19" +
                ",\"company_id\":35" +
                ",\"token\":\"fcfbca61-42ea-44b0-9949-23ff73da3997\"}\n" + "\n";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/maintain_record")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }


    /**
     * E700 设备删除
     */
    @Test
    public void updateDeviceModify() throws Exception {
        String  requestBody= "{\"client_type\":" +
                "\"\",\"device_id\":\"1\"" +
                ",\"token\":" + "\"81a7128b-2de0-486a-b566-7ab5b951ae70\",\"uid\":\"1\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * B514 查询设备详情
     */
    @Test
    public void selectDeviceDetail() throws Exception {
        String  requestBody="{\"query\":" +
                "{\"device_id\":\"1\"}" +
                ",\"client_type\":" + "2" +
                ",\"uid\":19" +
                ",\"company_id\":35" +
                ",\"token\":\"28d1b32b-c189-47ea-81b7-9ff6f7a1c65b\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }

    /**
     * 设备新增修改
     */
    @Test
    public void addAndUpdateDevice() throws Exception {
        String  requestBody= "{\"client_type\":\"\"" +
                ",\"company_id\":\"35\"" +
                ",\"uid\":\"19\"," +
                "\"token\":\"28d1b32b-c189-47ea-81b7-9ff6f7a1c65b\"" +
                ",\"device_info\":{\"device_id\":\"1\"" +
                ",\"" + "check_uid\":\"1\"" +
                ",\"check_uid_name\":\"lll\"" +
                ",\"device_addr\":\"南山区\"" +
                ",\"device_brand\":" +
                "\"测试内容\"" +
                ",\"pg_id\":\"1\"" +
                ",\"device_model\":\"HA01\"" +
                ",\"device_name\":\"测试内容 \"" +
                ",\"device_no\"" +
                ":1,\"device_pic\":12" +
                ",\"device_status\":0" +
                ",\"is_auto_pause\":\"1\"" +
                ",\"is_auto_prod\":\"1\"" +
                ",\"is_share\":" +
                "\"是\"" +
                ",\"labour_ratio\":\"5\"" +
                ",\"last_maintain_reason\":\"正常\"" +
                ",\"pg_name\":\"生产组\"" +
                ",\"prod_date\"" +
                ":\"2017-12-21\",\"work_life\":\"1\"}}";
        MvcResult mvcResult = mockMvc.perform(post("/api/pm/pg_device/modify")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn();
        Assert.assertNull(mvcResult.getModelAndView());
    }
}
