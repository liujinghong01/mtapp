package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.iv.warehouse.service.StoreCheckBillService;
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
@Api(value="仓库盘点单",description = "仓库盘点单")
public class StoreCheckBillController extends BaseController {
    @Autowired
    private StoreCheckBillService  storeCheckBillService;

    @ApiOperation(value = "E435 盘点单生成", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"company_id\":\"15\"" +
            ",\"company_name\":\"asd\"" +
            ",\"uid\":\"2\"" + "" +
            ",\"client_type\":\"\"" +
            ",\"token\":\"38d2e66f-27f4-487e-9eaf-c1f146c67a79\"" +
            ",\"check_info\":" +
            "{\"operator_id\":\"1\"" + "" +
            ",\"store_house_id\":\"1\"" +
            ",\"store_house_name\":\"dsn8\"" +
            ",\"mat_type_list\":[{\"mat_type_id\":1" +
            ",\"mat_type_name\":" + "\"测试内容xo8i\"}]}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_check_bill/add")
//    @RequiresPermissions(value = "enterprise:iv:store_check_bill:create")
    public Object storeCheckBillAdd(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            map.put("check_info", param.get("check_info") == null ? null : param.get("check_info"));
            Map<String, Object> retrunMap = storeCheckBillService.addStoreCheckBill(map);
            modelMap.addAttribute("description", "盘点单生成成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        }catch(NullPointerException ex){
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "盘点单生成失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E426 盘点单物料盘点", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"token\":" +
            "\"ed888c60-9bfb-4649-bbe1-da89d5d09287\"" + "" +
            ",\"company_id\":\"35\"" +
            ",\"uid\":\"19\"" +
            ",\"client_type\":\"2\"" + "" +
            ",\"check_info\":" +
            "{\"check_bill_id\":\"26\"" +
            ",\"check_bill_no\":\"A004\"" +
            "," + "\"check_profit_loss_price\":\"100\"" +
            ",\"check_profit_loss_qty\":\"10\"" + "" +
            ",\"check_profit_loss_reason\":\"物料缺少了\"" +
            ",\"check_real_price\":\"300\"" + "" +
            ",\"check_real_qty\":\"30\"" +
            ",\"liquidate_flag\":\"1\",\"mat_id\":\"3\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_check_bill/modify")
//    @RequiresPermissions(value = "enterprise:iv:store_check_bill:update")
    public Object storeCheckBillModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("check_info", param.get("check_info") == null ? null : param.get("check_info"));
            Map<String, Object> retrunMap = storeCheckBillService.updateStoreCheckBill(map);
            modelMap.addAttribute("description", "盘点单物料盘点成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "盘点单物料盘点失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E424 查询盘点单列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" +
            "{\"approve_step\":" +
            "[\"step12\",\"step11\"],\"liquidate_flag\":[2,1]}" + "" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\":\"12066064-7e71-47fb-96c7-ca8f528f1fe4\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_check_bill/list")
//    @RequiresPermissions(value = "enterprise:iv:store_check_bill:view")
    public Object selectStoreCheckList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = storeCheckBillService.selectStoreCheckBillList(map);
            modelMap.addAttribute("description", "查询盘点单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询盘点单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value ="E425 查询盘点单待盘已盘物料",notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"2\"" +
            ",\"uid\":\"19\"" +
            ",\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"token\":\"cc40a541-226e-4467-9697-90a0715b1b3e\"" +
            ",\"query\":" +
            "{\"check_bill_id\":\"8\"" +
            ",\"liquidate_flag\":\"2\"" +
            ",\"mat_desc\":\"a\"" +
            ",\"mat_no\":\"1\",\"mat_type_ids\":[2]}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_check_bill/detail")
//    @RequiresPermissions(value = "enterprise:iv:store_check_bill:view")
    public Object selectStoreCheckBillDetail(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap =storeCheckBillService.selectStoreCheckBillDetail(map);
            modelMap.addAttribute("description", "查询盘点单待盘已盘物料成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询盘点单待盘已盘物料失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
