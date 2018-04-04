package com.mt.qm.quality.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.qm.quality.service.PurchQcService;
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
* @create    2018/1/12 9:40
* @Modified By:
*/
@RestController
@Api(value ="来料质检管理",description = "来料质检管理")
public class PurchQcController extends BaseController{

    @Autowired
    private PurchQcService   purchQcService;


    @ApiOperation(value = "E788 查询来料质检列表")
    @ApiImplicitParam(name = "param",value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" +
                    "{\"approve_step\":[\"step20\"]}" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id" + "\":35" +
            ",\"token\":\"8e54e0e7-533a-440e-81b6-3d8bed180c62\"}")
    @PostMapping("/api/qm/purch_qc/list")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:view")
    public Object  selectPurchQcList(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = purchQcService.selectPurchQcList(map);
            modelMap.addAttribute("description", "查询来料质检单成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询来料质检单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E790 来料质检单删除", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"purch_qc_id\":\"\"" +
            ",\"token\":\"a6c1bee7-da50-4746-9961-5324eb5d59f8\"" +
            ",\"uid\":\"1\"}\n", required = true, dataType = "Map")
    @PostMapping("/api/qm/purch_qc_bill/delete")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:delete")
    public Object updatePurchQcBill(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("purch_qc_id", param.get("purch_qc_id") == null ? null : param.get("purch_qc_id"));
            Integer integer = purchQcService.updatePurchQcBill(map);
            if (integer > 0) {
                modelMap.addAttribute("description", "来料质检单删除成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        }catch (NullPointerException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "来料质检单删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E789 查询来料质检详情")
    @ApiImplicitParam(name = "param",value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"query\":" +
                    "{\"purch_qc_no\":" +
                    "\"PQ18010004\"}" +
            ",\"token\":\"889b51bc-0fa9-4462-bb0f-dc0be73e3a48\"}\n")
    @PostMapping("/api/qm/purch_qc/detail")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:view")
    public Object  selectQmPurchQcDetail(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = purchQcService.selectPurchQcDetail(map);
            modelMap.addAttribute("description", "查询来料质检详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询来料质检详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E846 查询待质检来料详情")
    @ApiImplicitParam(name = "param",value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"query\":" +
                    "{\"purch_no\":\"\"" +
                    ",\"purch_type\":\"\"" +
                    ",\"sup_name\":\"\"}" +
            ",\"token\":\"889b51bc-0fa9-4462-bb0f-dc0be73e3a48\"}")
    @PostMapping("/api/qm/purch_qc_stay/detail")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:view")
    public Object  selectStayCheckPurchDetail(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = purchQcService.selectStayCheckPurchDetail(map);
            modelMap.addAttribute("description", "查询待质检来料详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询待质检来料详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E791 新增修改来料质检单")
    @ApiImplicitParam(name = "param",value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"company_name\":\"联大\"" +
            ",\"token\":\"0c4e2193-c9f3-4b74-9099-e4e0dfdf3468\"" +
            ",\"uid\":\"19\"" +
            ",\"purch_qc_info\":{\"approve_sugg\":\"aaa\"" +
            ",\"sup_name\":\"联华\"" +
            ",\"qc_date\":\"2018-01-12\"" +
            ",\"purch_qc_no\":\"PQ18010004\"" +
                    ",\"purch_type\":\"1\"" +
            ",\"qc_detail_list\":[{\"purch_id\":1" +
                    ",\"purch_no\":\"MP000001\"" +
            ",\"purch_detail_list\":[{\"qc_bill_qty\":40" +
                    ",\"quantity\":100" +
                    ",\"mat_desc\":\"psi\"" +
                    ",\"purch_sub_id\":1" +
                    ",\"no_qc_qty\":60" +
                    ",\"mat_no\":\"HJ001\"" +
                    ",\"mat_type_id\":\"1\"" +
                    ",\"unusual_reason\":\"质量没问题\"" +
                    ",\"mat_model\":null" +
                    ",\"no_qualified_qty\":0" +
                    ",\"special_qty\":20" +
                    ",\"mat_id\":\"1\"" +
                    ",\"purch_qc_detail_id\":85" +
                    ",\"is_qc\":\"1\"" +
                    ",\"qc_qty\":50" +
                    ",\"qualified_qty\":10" +
                    ",\"mat_type_name\":\"1\"}" +
                    ",{\"qc_bill_qty\":70" +
                    ",\"quantity\":100" +
                    ",\"mat_desc\":\"asi\"" +
                    ",\"purch_sub_id\":10" +
                    ",\"no_qc_qty\":30" +
                    ",\"mat_no\":\"HJ002\"" +
                    ",\"mat_type_id\":\"2\"" +
                    ",\"unusual_reason\":\"质量没问题\"" +
                    ",\"mat_model\":null" +
                    ",\"no_qualified_qty\":0" +
                    ",\"special_qty\":20" +
                    ",\"mat_id\":\"1\"" +
                    ",\"purch_qc_detail_id\":86" +
                    ",\"is_qc\":\"1\"" +
                    ",\"qc_qty\":10" +
                    ",\"qualified_qty\":10" +
            ",\"mat_type_name\":\"1\"}]" +
            ",\"is_qc\":\"1\"}]" +
                    ",\"approve_step\":\"step10\"" +
                    ",\"inspector\":\"lll\"" +
                    ",\"purch_qc_id\":15," +
            "\"sup_company_id\":1}}")
    @PostMapping("/api/qm/purch_qc_bill/modify")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:create,update")
    public  Object   addAndUpdatePurchQcBill(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("purch_qc_info", StringUtils.isEmpty(param.get("purch_qc_info").toString()) == true ? null : param.get("purch_qc_info"));
            Map<String, Object> returnMap = purchQcService.addAndUpdatePurchQcBill(map);
            modelMap.addAttribute("description", "新增修改来料质检单成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (ArithmeticException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改来料质检单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E838 查询送货单质检详情")
    @ApiImplicitParam(name = "param",value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            "," + "\"query\":"
                    + "{\"delivery_no\":\"DM111\"}" +
            ",\"token\":" + "\"8475709a-a19f-4fc0-9af9-ceb336a7da82\"}")
    @PostMapping("/api/qm/delively_qc/detail")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:view")
    public Object  selectDeliveryBillPurDetail(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = purchQcService.selectDeliveryBillPurDetail(map);
            modelMap.addAttribute("description", "查询送货单质检详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询送货单质检详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E841 送货单生成质检单")
    @ApiImplicitParam(name = "param",value = "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"company_name\":\"联大\"" +
            ",\"token\":\"8475709a-a19f-4fc0-9af9-ceb336a7da82\"" +
            ",\"uid\":\"19\"" +
            ",\"purch_qc_info\":" +
                    "{\"approve_sugg\":\"aaa\"" +
                    ",\"sup_name\":\"联华\"" +
                    ",\"qc_date\":\"2018-01-12\"" +
                    ",\"purch_qc_no\":\"PQ18010004\"" +
                    ",\"purch_type\":\"1\"" +
            ",\"qc_detail_list\":" +
                    "[{\"purch_id\":21" +
                    ",\"is_qc\":\"1\"" +
                    ",\"purch_no\":\"BA17120068\"" +
            ",\"delivery_detail_list\":" +
                    "[{\"qc_bill_qty\":90" +
                    ",\"quantity\":100" +
                    ",\"mat_desc\":\"psi\"" +
                    ",\"purch_sub_id\":1" +
                    ",\"no_qc_qty\":10" +
                    ",\"mat_no\":\"HJ001\"" +
                    ",\"mat_type_id\":1" +
                    ",\"unusual_reason\":\"质量没问题ss\"" +
                    ",\"mat_model\":null" +
                    ",\"no_qualified_qty\":0" +
                    ",\"special_qty\":20" +
                    ",\"mat_id\":\"1\"" +
                    ",\"purch_qc_detail_id\":85" +
                    ",\"is_qc\":\"1\"" +
                    ",\"qc_qty\":20" +
                    ",\"qualified_qty\":10" +
            ",\"mat_type_name\":\"1\"}]}]" +
                    ",\"approve_step\":\"step10\"" +
                    ",\"inspector\":\"lll\"" +
                    ",\"purch_qc_id\":\"\"" +
            ",\"sup_company_id\":1}}\n")
    @PostMapping("/api/qm/delively_qc_bill/save")
    @RequiresPermissions(value = "enterprise:qm:mat_qc:create")
    public Object  saveDeliveryBillPurDetail(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String  clientType=param.get("client_type")==null?null:param.get("client_type").toString().trim();
            Map<String,Object>   map=new HashMap();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("delivery_qc_info",StringUtils.isEmpty(param.get("delivery_qc_info").toString())==true?null:param.get("delivery_qc_info"));
            Map<String,Object>  returnMap=purchQcService.addDeliveryNoPurBill(map);
            modelMap.addAttribute("description", "送货单生成质检单成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "送货单生成质检单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}