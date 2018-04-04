package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.PcInquirySupService;
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
* @create    2018/1/26 15:11
* @Modified By:
*/    
@RestController
@Api(value = "查询供应商询价反馈单", description = "查询供应商询价反馈单")
public class PcInquirySupController extends BaseController {
    @Autowired
    private PcInquirySupService pcInquirySupService;

    @ApiOperation(value = "E268 查询供应商询价反馈单列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\"" + ":" +
                    "{\"deal_flag\":\"1\"" +
                    ",\"mat_desc\":\"物料1\"}" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            "," + "\"token\":\"e78bd01f-ae28-4d4d-b9dd-375a83671c39\"}", required = true, dataType = "Map")
    @PostMapping("/api/pc/sup_inquiry_feedback/list")
    @RequiresPermissions(value = "enterprise:pc:inquiry_feedback:view")
    public Object selectFeedBackList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null ? 10 : Integer.valueOf(param.get("page_size").toString()));
            map.put("company_id", param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = pcInquirySupService.selectFeedBackList(map);
            modelMap.addAttribute("description", "查询供应商询价反馈单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询供应商询价反馈单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value ="E269 查询供应商询价反馈单详情",notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"uid\":\"1\"" +
            ",\"company_id\":\"35\"" +
            ",\"query\":" +
                    "{\"inquiry_id\":\"31\"}}"
            , required = true, dataType = "Map")
    @PostMapping("/api/pc/sup_inquiry_feedback/detail")
    @RequiresPermissions(value = "enterprise:pc:inquiry_feedback:view")
    public Object selectFeedBackDetail(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            Map<String, Object> returnMap = pcInquirySupService.selectFeedBackDetail(map);
            modelMap.addAttribute("description", "查询供应商询价反馈单详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (NullPointerException  e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询供应商询价反馈单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value ="E276 询价单提交反馈",notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"uid\":\"2\"" +
            ",\"client_type\":\"\"" +
            ",\"token\":\"0f7934f7-82ca-4dcc-955a-1ea26dbdb56d\"" +
            ",\"company_id\":\"119\"" +
            ",\"inquiry_feedback\":" +
            "{\"mat_list\":" +
            "[{\"process_up\":null" +
            ",\"quantity\":60" +
            ",\"total_price\":60" +
            ",\"mat_desc\":null" +
            ",\"feedback_detail_id\":189" +
            ",\"mat_no\":" +
            "\"M2017004-20171208101620-20171208101620\"" +
            ",\"mat_type_id\":4" +
            ",\"mat_model\":\"Φ:10 H:10\"" +
            ",\"tax_cost\":null" +
            ",\"process_cost\":0" +
            ",\"mat_id\":\"263\"" +
            ",\"up\":1" +
            ",\"mat_type_name\":\"毛坯\"}]" +
            ",\"delivery_place\":\"sz\"" +
            ",\"total_price\":5" + "0" +
            ",\"tax_ratio\":null" +
            ",\"feedback_id\":192" +
            ",\"inquiry_sup_id\":\"192\"" +
            ",\"remark\":\"测试内容k73n\"" +
            ",\"purch_man_name\":\"xiong1112@qq.com1f1\"" +
            ",\"sup_name\":\"李玲珑\"" +
            ",\"delivery_date\":\"2018-01-04\"" +
            ",\"coin_name\":\"人民币\"" +
            ",\"quote_end_date\":\"1975-01-01\"" +
            ",\"purch_man_phone\":null" +
            ",\"purch_man_email\":\"xiong@qq1.com\"" +
            ",\"invoice_type\":99" +
            ",\"deal_flag\":2" +
            ",\"invoice_type_name\":\"收据\"" +
            ",\"purch_man\":47441" +
            ",\"quote_date\":\"2018-01-04\"" +
            ",\"sup_company_id\":38" +
            ",\"coin\":\"RMB\"" +
            ",\"inquiry_date\":\"2018-01-04\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pc/sup_inquiry_feedback/submit")
    @RequiresPermissions(value = "enterprise:pc:inquiry_deal:update")
    public Object updateInquirySup(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("inquiry_feedback", StringUtils.isEmpty(param.get("inquiry_feedback").toString()) == true ? null : param.get("inquiry_feedback"));
            Map<String, Object> retrunMap = pcInquirySupService.updateInquirySup(map);
            modelMap.addAttribute("description", "询价单提交反馈成功！");
            return setModelMap(modelMap, HttpCode.OK,retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "询价单提交反馈失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
