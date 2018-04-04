package com.mt.iv.warehouse.controller;


import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.iv.warehouse.service.ScrapMatBillService;
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
* @create    2018/1/26 15:10
* @Modified By:
*/    
@RestController
@Api(value="报废单",description = "报废单")
public class ScrapMatBillController extends BaseController{

    @Autowired
    private ScrapMatBillService  scrapMatBillService;

    @ApiOperation(value = "E418 查询报废单列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" +
                    "{\"approve_step\":\"\"}" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\":\"b883c458-d4f5-494c-a8bb-7e478565bc5f\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/scrap_mat_bill/list")
    @RequiresPermissions(value = "enterprise:iv:scrap_bill:view")
    public Object selectScrapMatList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = scrapMatBillService.selectScrapMatBillList(map);
            modelMap.addAttribute("description", "查询报废单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询报废单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value ="E419 查询报废单详情",notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"query\":" +
                    "{\"scrap_id\":\"1\"}" +
            ",\"client_type\":2" + "" +
            ",\"uid\":19" +
            ",\"token\":\"b883c458-d4f5-494c-a8bb-7e478565bc5f\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/scrap_mat_bill/detail")
    @RequiresPermissions(value = "enterprise:iv:scrap_bill:view")
    public Object selectScrapMatDetail(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap =scrapMatBillService.selectScrapMatBillDetail(map);
            modelMap.addAttribute("description", "查询报废单详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询报废单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E421 报废单新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"uid\":\"2\"" +
            ",\"token\":\"32578432-987b-4e4d-a48e-6a0b1e860c1f\"" +
            ",\"scrap_mat_info\"" +
            ":{\"approve_step\":\"step15\"" +
            ",\"approve_sugg\":\"测试内容3434\"" +
            ",\"dep_id\":\"1\"" +
            ",\"dep_name\":\"设计部\"" +
            ",\"request_date\":\"2017-08-22\"" +
            ",\"scrap_no\":\"001\"" +
            ",\"scrap_detail_list\":" +
            "[{\"store_house_id\":\"1\"" +
            ",\"store_house_name\":\"\"" +
            ",\"store_room_id\":\"1\"" +
            ",\"store_room_name\":\"\"" +
            ",\"deal_flag\":\"2\"" +
            ",\"mat_desc\":\"asdas\"" +
            ",\"mat_id\":28876" +
            ",\"mat_model\":\"测试内容5633\"" +
            ",\"mat_no\":\"测试内容4a35\"" +
            ",\"mat_type_id\":41348" +
            ",\"mat_type_name\":\"测试内容xo8i\"" +
            ",\"quantity\":86541" +
            ",\"weight\":74350" +
            ",\"remark\":\"asdsad\"" +
            ",\"unit\":\"assa\"}" +
            ",{\"store_house_id\":\"1\"" +
            ",\"store_house_name\":\"\"" +
            ",\"store_room_id\":\"1\"" +
            ",\"store_room_name\":\"\"" +
            ",\"deal_flag\":\"1\"" +
            ",\"mat_desc\":\"aaaa\"" +
            ",\"mat_id\":28876" +
            ",\"mat_model\":\"测试内容5683\"" +
            ",\"mat_no\":\"测试内容4a35\"" +
            ",\"mat_type_id\":41348" +
            ",\"mat_type_name\":\"测试内容xo8is\"" +
            ",\"quantity\":86541" +
            ",\"weight\":74350" +
            ",\"remark\":\"asdsad\"" +
            ",\"unit\":\"assa\"}]}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/scrap_mat_bill/modify")
    @RequiresPermissions(value = "enterprise:iv:scrap_bill:create,update")
    public Object scrapMatAddAndModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("scrap_mat_info", param.get("scrap_mat_info") == null ? null : param.get("scrap_mat_info"));
            Map<String, Object> retrunMap =scrapMatBillService.addAndUpdateScrapMatBill(map);
            modelMap.addAttribute("description", "报废单新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "报废单新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E434 执行报废", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"uid\":\"19\"" +
            ",\"client_type\":\"2\"" +
            ",\"company_id\":\"35\"" +
            ",\"scrap_id\":\"5\"" +
            ",\"mat_id\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/scrap_mat_bill/scrap")
    @RequiresPermissions(value = "enterprise:iv:scrap_bill:exec_scrap_bill:create,update")
    public Object executeScrap(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("scrap_id", param.get("scrap_id") == null ? null : param.get("scrap_id"));
            Map<String, Object> retrunMap = scrapMatBillService.executeScrap(map);
            modelMap.addAttribute("description", "执行报废成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        }catch (IllegalArgumentException e){
            modelMap.addAttribute("description",e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "执行报废失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
