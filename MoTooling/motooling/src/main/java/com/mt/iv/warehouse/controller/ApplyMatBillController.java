package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.iv.warehouse.service.ApplyMatBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
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
@Api(value = "领料单", description = "领料单")
public class ApplyMatBillController extends BaseController{

    @Autowired
    private ApplyMatBillService  applyMatBillService;

    @ApiOperation(value = "E393 领料单新增修改", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"apply_mat_info\":" +
            "{\"approve_sugg\":\"此单可以通过审批\"" +
            ",\"dep_id\":47" +
            ",\"apply_no\":\"\"" +
            ",\"request_date\":\"2017-12-12\"" +
            ",\"apply_detail_list\":" +
            "[{\"detail_id\":\"\"" +
            ",\"applied_qty\":\"20\"" +
            ",\"mat_no\":\"S003\"" +
            ",\"mat_model\":\"HKJLs\"" +
            ",\"unit\":\"assa\"" +
            ",\"quantity\":5" +
            ",\"mat_id\":1" +
            ",\"mat_desc\":\"asd\"" +
            ",\"weight\":null" +
            ",\"deal_flag\":2" +
            ",\"mat_type_name\":\"测试内容xo8issss\"" +
            ",\"mat_type_id\":1}" +
            ",{\"detail_id\":\"\"" +
            ",\"applied_qty\":\"20\"" +
            ",\"total_price\":null" +
            ",\"mat_desc\":\"asd\"" +
            ",\"mat_no\":\"S002\"" +
            ",\"mat_type_id\":1" +
            ",\"mat_model\":\"HKJLs\"" +
            ",\"mat_id\":1" +
            ",\"stock_qty\":null" +
            ",\"mat_type_name\":\"测试内容xo8isss\"" +
            ",\"quantity\":3}]" +
            ",\"apply_id\":\"\"" +
            ",\"approve_step\":\"step10\"" +
            ",\"dep_name\":\"生产部\"" +
            ",\"aplly_type\":\"1\"" +
            ",\"applied_qty\":\"10\"" +
            ",\"mold_no\":\"HS01\"" +
            ",\"bom_type\":\"1\"}" +
            ",\"client_type\":2" +
            ",\"token\":\"ea68a108-20ea-43bd-bcf9-8829beb62671\"" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"company_name\":\"as\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/apply_mat_bill/modify")
    //符合  value 数组中 任何一个 权限 都能匹配   部门领料单 。 模具领料单
    @RequiresPermissions(value = {"enterprise:iv:apply_mat_billd:create,update","enterprise:pm:mould_apply_mat_bill:create,update"},logical= Logical.OR)
    public Object applyMatAddAndModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("apply_mat_info", param.get("apply_mat_info") == null ? null : param.get("apply_mat_info"));
            Map<String, Object> retrunMap = applyMatBillService.addAndUpdateApplyMatBill(map);
            modelMap.addAttribute("description", "领料单新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        }catch (ArithmeticException  e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "领料单新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E390 查询领料单列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" +
                    "{\"approve_step\":[\"step10\"]" +
                    ",\"aplly_type\":[0]" + "}" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\":\"8e54e0e7-533a-440e-81b6-3d8bed180c62\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/apply_mat_bill/list")
    @RequiresPermissions(value = "enterprise:iv:apply_mat_bill:view")
    public Object selectApplyMatList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = applyMatBillService.selectApplyMatBillList(map);
            modelMap.addAttribute("description", "查询领料单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询领料单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value ="B391 查询领料单详情",notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":" +
            ",\"company_id\":\"35\"" +
            ",\"query\":{\"apply_id\":\"1\"}" +
            ",\"token\":\"69ed7992-f150-49a6-9c78-b1525f0bc8d3\"" +
            ",\"uid\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/apply_mat_bill/detail")
    @RequiresPermissions(value = "enterprise:iv:apply_mat_bill:view")
    public Object selectApplyMatDetail(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap =applyMatBillService.selectApplyMatBillDetail(map);
            modelMap.addAttribute("description", "查询领料单详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询领料单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
    @ApiOperation(value = "E483 领料单删除", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":" +
            ",\"apply_id\":\"77\"" +
            ",\"token\":\"a6c1bee7-da50-4746-9961-5324eb5d59f8\"" +
            ",\"uid\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/apply_mat_bill/delete")
    @RequiresPermissions(value = "enterprise:iv:apply_mat_bill:delete")
    public Object updateApplyMatModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("apply_id", param.get("apply_id") == null ? null : param.get("apply_id"));
            Integer integer = applyMatBillService.updateApplyBill(map);
            if (integer > 0) {
                modelMap.addAttribute("description", "领料单删除成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        }catch (NullPointerException  e){
            modelMap.addAttribute("description",e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "领料单删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
