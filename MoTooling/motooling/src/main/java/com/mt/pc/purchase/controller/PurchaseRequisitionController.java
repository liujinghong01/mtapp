package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.PurchaseRequisitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "请购单操作", description = "请购单操作")
public class PurchaseRequisitionController extends BaseController{
    private final Logger logger = Logger.getLogger(PurchaseRequisitionController.class);
    @Autowired
    private PurchaseRequisitionService purchaseRequisitionService;


    @ApiOperation(value = "E209 新增修改请购单", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"84d599bb-74ef-48a2-8ee0-25158f25b2bf\",\"company_id\":\"35\",\"company_name\":\"这这\"," +
            "\"head_info\":{\"dep_id\":21,\"dep_name\":\"as11\",\"reason\":\"当然买\",\"request_date\":\"2017-10-10\",\"request_id\":\"1\"," +
            "\"request_man\":\"1\",\"request_man_name\":\"1\",\"request_no\":\"\",\"line_list\":[{\"delivery_date\":\"2018-5-20\",\"line_id\":\"5\"," +
            "\"mat_desc\":\"shenmme么鬼\",\"mat_id\":\"1\",\"mat_model\":\"21\",\"mat_no\":\"123\",\"mat_type_id\":\"123\",\"mat_type_name\":\"413124\"," +
            "\"price\":\"20\",\"quantity\":\"5\",\"remark\":\"sfdsdf\",\"unit\":\"kg\",\"up\":\"650000\",\"weight\":\"80\"},{\"delivery_date\":\"2018-5-20\"," +
            "\"line_id\":\"\",\"mat_desc\":\"11什么鬼\",\"mat_id\":\"2\",\"mat_model\":\"214\",\"mat_no\":\"12344\",\"mat_type_id\":\"12344\"," +
            "\"mat_type_name\":\"41312444\",\"price\":\"20\",\"quantity\":\"5\",\"remark\":\"sfdsdf\",\"unit\":\"kg\",\"up\":\"650000\"," +
            "\"weight\":\"80\"}]}}", required = true, dataType = "Map")
    @PostMapping("/api/pc/request/modify")
    @RequiresPermissions(value = "enterprise:pc:request_order:create,update")
    public Object addAndupdateModefy(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", StringUtils.isEmpty(param.get("uid").toString()) == true ? null : param.get("uid"));
            map.put("companyName", param.get("company_name")  ==null || param.get("company_name")  ==""  ? null : param.get("company_name"));
            map.put("head_info", StringUtils.isEmpty(param.get("head_info").toString()) == true ? null : param.get("head_info"));
            Map<String, Object> returnMap =purchaseRequisitionService.addAndupdateModefy(map);
            modelMap.addAttribute("description", "新增修改请购单成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改请购单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E206 删除请购单", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"5797882e-08be-4944-a0bb-f7a503453e12\", \"request_id\":1 }", required = true, dataType = "Map")
    @PostMapping("/api/pc/request/delete")
    @RequiresPermissions(value = "enterprise:pc:request_order:delete")
    public Object deleteRequest(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Long  requestId = StringUtils.isEmpty(param.get("request_id").toString()) == true ? null : Long.valueOf(param.get("request_id").toString());
            Boolean b =purchaseRequisitionService.deleteRequest(requestId);
            if(b){
                modelMap.addAttribute("description", "删除请购单成功！");
                return setModelMap(modelMap, HttpCode.OK);
            }else {
                modelMap.addAttribute("description", "参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除请购单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E207 删除请购单行信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"token\":\"138f3dfa-3bbd-4900-bfa7-6c215fd7c657\",\n" +
            "\"line_ids\":[1,2]\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pc/request/deleteLine")
    @RequiresPermissions(value = "enterprise:pc:request_order:delete")
    public Object deleteRequestLine(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            List<Long> lineIds = StringUtils.isEmpty(param.get("line_ids").toString()) == true ? null : (List<Long>) param.get("line_ids");
            Boolean b =purchaseRequisitionService.deleteRequestLine(lineIds);
            if(b){
                modelMap.addAttribute("description", "删除请购单行信息成功！");
                return setModelMap(modelMap, HttpCode.OK);
            }else {
                modelMap.addAttribute("description", "参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除请购单行信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E229 查询请购单详情", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"5797882e-08be-4944-a0bb-f7a503453e12\", \"request_id\":1 }", required = true, dataType = "Map")
    @PostMapping("/api/pc/query/request")
    @RequiresPermissions(value = "enterprise:pc:request_order:view")
    public Object queryRequest(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object>  query = param.get("query") == null ? null : (Map<String, Object>) param.get("query");

            Long requestId = query.get("request_id") == null ||  query.get("request_id") == ""? null : Long.valueOf(query.get("request_id").toString());
            Map<String, Object> returnMap =purchaseRequisitionService.queryRequest(requestId);
            modelMap.addAttribute("description", "查询请购单详情成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询请购单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E220 查询请购单列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"8f959b5b-9485-4b69-969f-33fed08ee6f2\",\n" +
            "\"company_id\": \"35\", \"query\":{\"approve_step\":\"step10\"} }", required = true, dataType = "Map")
    @PostMapping("/api/pc/query/requestList")
    @RequiresPermissions(value = "enterprise:pc:request_order:view")
    public Object queryRequestList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap =purchaseRequisitionService.queryRequestList(map);
            modelMap.addAttribute("description", "查询请购单列表成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询请购单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E208 参考请购单", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"8f959b5b-9485-4b69-969f-33fed08ee6f2\",\n" +
            "\"company_id\": \"35\", \"query\":{\"approve_step\":\"step10\"},\"curr_page\":1,\n" +
            " \"page_size\":2}", required = true, dataType = "Map")
    @PostMapping("/api/pc/request/referHis")
    @RequiresPermissions(value = "enterprise:pc:request_order:create")
    public Object requestRefurHis(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("companyName", StringUtils.isEmpty(param.get("company_name").toString()) == true ? null : param.get("company_name"));
            map.put("uid", StringUtils.isEmpty(param.get("uid").toString()) == true ? null : param.get("uid"));
            map.put("requestIdHis",param.get("request_id_his") == null ? null : Long.valueOf(param.get("request_id_his").toString()));
            Map<String, Object> returnMap =purchaseRequisitionService.addRequestRefurHis(map);
            modelMap.addAttribute("description", "参考请购单成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "参考请购单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E224 新增修改请购单行明细信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"line_list\":[{\"quantity\":5,\"mat_desc\":\"11什么鬼\",\"weight\":80,\"remark\":\"sfdsdf\",\"line_id\":21,\"mat_no\":\"12344\",\"mat_type_id\":12344,\"delivery_date\":\"2018-05-20\",\"mat_model\":\"214\",\"unit\":\"kg\",\"price\":20,\"mat_id\":\"2\",\"up\":650000,\"mat_type_name\":\"41312444\"}],\"client_type\":2,\"token\":\"1040fc51-4b68-4b04-931b-b65afd1f0390\",\"uid\":19,\"company_id\":35}", required = true, dataType = "Map")
    @PostMapping("/api/pc/request/modifyLine")
    @RequiresPermissions(value = "enterprise:pc:request_order:create,update")
    public Object addAndUpdateModifyLine(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            if(param.containsKey("request_id")){
                map.put("request_id", param.get("request_id") == null ||param.get("request_id") =="" ? null : param.get("request_id"));
            }
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("line_list", StringUtils.isEmpty(param.get("line_list").toString()) == true ? null : param.get("line_list"));
            Map<String, Object> returnMap =purchaseRequisitionService.addAndUpdateModifyLine(map);
            modelMap.addAttribute("description", "新增修改请购单行明细信息成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改请购单行明细信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}
