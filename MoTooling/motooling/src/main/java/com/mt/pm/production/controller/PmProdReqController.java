package com.mt.pm.production.controller;


import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pm.common.model.PmProdReq;
import com.mt.pm.production.service.PmProdReqService;
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


@RestController
@Api(value = "查询模具物料需求", description = "查询模具物料需求")
public class PmProdReqController extends BaseController{
    @Autowired
    private PmProdReqService   pmProdReqService;

    @ApiOperation(value = "E747 查询模具物料需求", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"company_id\":\"35\"" +
            ",\"query\":" +
                    "{\"apply_type\":\"1\""
                    + ",\"bom_type\":\"\"" +
                    ",\"mold_no\":\"teest3\"}" +
            ",\"token\":" + "\"6397ad4c-03e3-45e9-8bdb-944e8848c154\"" +
            ",\"uid\":\"" + "19\"" +
            ",\"client_type\":\"\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/mold_mat_req/list")
    @RequiresPermissions(value = "enterprise:pm:mould_apply_mat_bill:view")
    public Object selectDeviceCheckAllocate(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String client_type = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmProdReqService.selectMouldMatterDemand(map);
            modelMap.addAttribute("description", "查询模具物料需求成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (NullPointerException e){
            modelMap.addAttribute("description",e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询模具物料需求失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E001 按Bom查询生产定单列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"mold_no\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_req_bom/list")
    @RequiresPermissions(value = "enterprise:pm:routings:view")
    public Object getProdOrderListByBomConditions(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("query", param.get("query") == null || param.get("query").toString().isEmpty() ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = pmProdReqService.selectProdReqByBomConditions(map);
            modelMap.addAttribute("description", "获取生产定单列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产定单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E002 按物料查询生产定单列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"mat_no\":\"\",\n" +
            "    \"mold_no\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_req_mat/list")
    @RequiresPermissions(value = "enterprise:pm:routings:view")
    public Object getProdOrderListByMatConditions(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("query", param.get("query") == null || param.get("query").toString().isEmpty() ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = pmProdReqService.selectProdReqByMatConditions(map);
            modelMap.addAttribute("description", "获取生产定单列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产定单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E003 查询BOM节点列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"bom_id\":\"\",\n" +
            "    \"node_id\":\"\",\n" +
            "    \"bom_type\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_req_bom_node/list")
    @RequiresPermissions(value = "enterprise:pm:routings:view")
    public Object getProdOrderListByNode(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {


            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap=pmProdReqService.selectProdReqByNode(map);

            modelMap.addAttribute("description", "查询BOM节点列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询BOM节点列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E006 查询生产需求详细")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"req_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_req/info")
    @RequiresPermissions(value = "enterprise:pm:routings:view")
    public Object getPmProdReqInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("req_id")==null)
            {
                dataMap.put("req_id", null);
            }
            else {
                Long reqId = Long.parseLong(map.get("req_id").toString());
                PmProdReq req=pmProdReqService.selectProdReqByPrimaryKey(reqId);
                Map<String, Object> confMap = this.camelToUnderline(BeanToMapUtil.convertBean(req));
                dataMap.put("node_req_info", confMap);
            }
            modelMap.addAttribute("description", "查询生产需求详细，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产需求详细失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
