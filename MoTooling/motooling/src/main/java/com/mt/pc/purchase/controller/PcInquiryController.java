package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.PcInquiryService;
import com.mt.pc.purchase.service.PcInquirySubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/26 15:12
* @Modified By:
*/    
@RestController
@Api(value = "查询待处理询价单列表", description = "查询待处理询价单列表")
public class PcInquiryController  extends BaseController {

    @Autowired
    private PcInquiryService  pcInquiryService;

    @Autowired
    private PcInquirySubService  pcInquirySubService;

    @ApiOperation(value = "E251 查询待处理询价单列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":" +
            ",\"uid\":\"1\"" +
            ",\"company_id\":\"103\"" +
            ",\"curr_page\":\"1\"" +
            ",\"page_size\":\"20\"" +
            ",\"token\"" + ":\"dbea7ca8-6432-41fb-a4ba-51758a0efde4\"}", required = true, dataType = "Map")
    @PostMapping("/api/pc/inquiry_deal/list")
    @RequiresPermissions(value = "enterprise:pc:inquiry_deal:view")
    public  Object   selectInquiryList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null ? 10 : Integer.valueOf(param.get("page_size").toString()));
            map.put("company_id", param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            Map<String, Object> retrunMap = pcInquiryService.selectInquiryList(map);
            modelMap.addAttribute("description", "查询待处理询价单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询待处理询价单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E271查询询价单详情",notes = "")
    @ApiImplicitParam(name="param",value=
            "{\"client_type\":\"\"," +
            "\"uid\":\"1\"," +
            "\"query\":" +
                    "{\"inquiry_id\":\"156\"}" +
            ",\"token\":\"f7a5f723-b66a-493f-9fba-b89405c5894f\"}",required =true,dataType ="Map")
    @PostMapping("/api/pc/inquiry/detail")
    @RequiresPermissions(value = "enterprise:pc:inquiry_deal:view")
    public  Object  selectInquiryDetail(@RequestBody Map<String,Object>param,ModelMap  modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pcInquiryService.selectInquiryDetail(map);
            modelMap.addAttribute("description", "查询询价单详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询询价单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value="E254查询询价反馈单详情",notes = "")
    @ApiImplicitParam(name="param",value=
            "{\"client_type\":" +
            ",\"uid\":\"1\"" +
            ",\"company_id\":" +
            "," + "\"query\":" +
                    "{\"inquiry_id\":\"158\"" +
                    ",\"feedback_id\":\"\"}" +
            ",\"token\":\"0f7934f7-82ca-4dcc-955a-1ea26dbdb56d\"}\n",required =true,dataType ="Map")
    @PostMapping("/api/pc/inquiry_feedback/detail")
    @RequiresPermissions(value = "enterprise:pc:inquiry_feedback:view")
    public  Object  selectInquiryFeedBackDetail(@RequestBody Map<String,Object>param,ModelMap  modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            Map<String, Object> returnMap = pcInquiryService.selectInquiryFeedBackDetail(map);
            modelMap.addAttribute("description", "查询询价反馈单详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (NullPointerException  e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询询价反馈单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E275 询价单发送（即创建）", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"inquiry_info\":" +
            "{\"coin\":\"RMB\"" +
            ",\"coin_name\":\"人民币\"" +
            ",\"deal_flag\":0" +
            ",\"delivery_date\":\"2018-1-04\"" +
            ",\"delivery_place\":\"sz\"" +
            ",\"inquiry_date\":\"2018-1-04\"" +
            ",\"inquiry_id\":\"\"" +
            ",\"invoice_type\":\"99\"" +
            ",\"invoice_type_name\":\"收据\"" +
            ",\"rebate\":1" +
            ",\"mat_list\":" +
            "[{\"mat_desc\":null" +
            ",\"mat_id\":263" +
            ",\"mat_model\":\"Φ:10 H:10\"" +
            ",\"mat_no\":\"M2017004-20171208101620-20171208101620\"" +
            ",\"mat_type_id\":4" +
            ",\"mat_type_name\":\"毛坯\"" +
            ",\"process_cost\":\"0.00\"" +
            ",\"process_up\":\"\"" +
            ",\"require_id\":11" +
            ",\"plan_qty\":100" +
            ",\"quantity\":50" +
            ",\"tax_cost\":\"\"" +
            ",\"total_price\":\"50.00\"" +
            ",\"up\":\"1\"}]" +
            ",\"purch_man\":47441" +
            ",\"purch_man_email\":\"xiong@qq1.com\"" +
            ",\"purch_man_name\":\"xiong1112@qq.com1f1\"" +
            ",\"quote_date\":\"2018-1-04\"" +
            ",\"quote_end_date\":\"1975-01-01\"" +
            ",\"remark\":\"测试内容k73n\"" +
            ",\"sup_list\":[{\"sup_company_id\":38" +
            ",\"sup_name\":\"李玲珑\"}" +
            ",{\"sup_company_id\":39" +
            ",\"sup_name\":\"大大雄\"}]" +
            ",\"tax_ratio\":0" +
            ",\"total_price\":\"50.00\"}" +
            ",\"client_type\":2" +
            ",\"token\":\"7f036f5b-0566-41ea-9b10-10e14be9b0c1\"" +
            ",\"uid\":19,\"company_id\":35}", required = true, dataType = "Map")
    @PostMapping("/api/pc/inquiry/send")
    @RequiresPermissions(value = "enterprise:pc:inquiry_feedback:create")
    public Object addPurchOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object> map = new HashMap();
            map.put("inquiry_info", StringUtils.isEmpty(param.get("inquiry_info").toString()) == true ? null : param.get("inquiry_info"));
            map.put("uid", param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString()));
            map.put("company_id", param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            Map<String, Object> retrunMap = pcInquirySubService.addInquirySub(map);
            modelMap.addAttribute("description", "询价单发送成功！");
            return setModelMap(modelMap, HttpCode.OK,retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "询价单发送失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
