package com.mt.qm.quality.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.qm.quality.service.ProcQcService;
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
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */

@RestController
@Api(value="工序质检单",description = "工序质检单")
public class ProcQcController extends BaseController{

    @Autowired
    private ProcQcService procQcService;

    @ApiOperation(value="E794 查询工序质检列表", notes = "查询工序质检列表")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map" ,value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"35\",\n" +
            "  \"curr_page\": 1,\n" +
            "  \"page_size\": 5,\n" +
            "  \"uid\": \"137\",\n" +
            "  \"inquiry_id\": \"1\",\n" +
            "  \"query\": {\n" +
            "    \"approveStepArray\": [\n" +
            "      \"step20\",\n" +
            "      \"step30\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"token\": \"22850417-e049-4a2a-b78a-ad7e1b0929fe\"\n" +
            "}")
    @PostMapping("/api/qm/proc_qc/list")
    @RequiresPermissions(value = "enterprise:qm:pop_qc:view")
    public Object selectProcQcList(@RequestBody Map<String,Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>(16);
            map.put("company_id", param.get("company_id").toString() == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid").toString() == null ? null : param.get("uid"));
            map.put("query", param.get("query").toString() == null ? null : param.get("query"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            Map<String, Object> returnMap = procQcService.selectProcQcList(map);
            modelMap.addAttribute("description", "查询工序质检单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询工序质检单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E797 查询工序质检详情", notes = "查询工序质检详情")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"\",\n" +
            "  \"uid\": \"137\",\n" +
            "  \"query\": {\n" +
            "    \"pop_qc_id\": 1\n" +
            "  },\n" +
            "  \"token\": \"0fd4757f-4626-4f6b-8842-2b42b7af7b9f\"\n" +
            "}")
    @PostMapping("/api/qm/proc_qc/detail")
    @RequiresPermissions(value = "enterprise:qm:pop_qc:view")
    public Object selectProcQcDetail(@RequestBody Map<String,Object> param,ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>(16);
            map.put("uid", param.get("uid").toString() == null ? null : param.get("uid"));
            map.put("query", param.get("query").toString() == null ? null : param.get("query"));
            map.put("company_id", param.get("company_id").toString() == null ? null : param.get("company_id"));
            Map<String, Object> returnMap = procQcService.selectProcQcDetail(map);
            modelMap.addAttribute("description", "查询工序质检详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询工序质检详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E836 查询待质检的工序信息", notes = "查询待质检的工序信息")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"\",\n" +
            "  \"uid\": \"137\",\n" +
            "  \"query\": {\n" +
            "    \"proc_name\": \"抛光\",\n" +
            "\"po_no\":\"\",\n" +
            "\"confirm_no\":\"\",\n" +
            "\"mat_no\":\"\"\n" +
            "  },\n" +
            "  \"token\": \"8cc52ef9-9653-415f-800b-fcbeb5561c69\"\n" +
            "}")
    @PostMapping("/api/qm/proc_qc_pqi/list")
    @RequiresPermissions(value = "enterprise:qm:pop_qc:view")
    public Object selectNoProcQcList(@RequestBody Map<String,Object> param,ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>(16);
            map.put("uid", param.get("uid").toString() == null ? null : param.get("uid"));
            map.put("query", param.get("query").toString() == null ? null : param.get("query"));
            map.put("company_id", param.get("company_id").toString() == null ? null : param.get("company_id"));
            Map<String,Object> returnMap = procQcService.selectNoProcQcList(map);
            modelMap.addAttribute("description", "查询待工序质检信息成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询待工序质检信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E799 新增修改工序质检单", notes = "新增修改工序质检单")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"35\",\n" +
            "  \"uid\": \"137\",\n" +
            "  \"token\": \"1c3ea548-bf60-4cf3-91ab-121954577b12\",\n" +
            "  \"proc_qc_info\": {\n" +
            "    \"inspector\": \"\",\n" +
            "    \"pop_qc_id\": \"42\",\n" +
            "    \"pop_qc_no\": \"\",\n" +
            "    \"qc_date\": \"\",\n" +
            "    \"prod_order_list\": [\n" +
            "      {\n" +
            "        \"confirm_no\": \"1\",\n" +
            "        \"is_submit_fault\": \"1\",\n" +
            "        \"mat_id\": \"1\",\n" +
            "        \"mat_name\": \"1\",\n" +
            "        \"mat_no\": \"1\",\n" +
            "        \"no_qualified_qty\": \"1\",\n" +
            "        \"pg_id\": \"1\",\n" +
            "        \"pg_name\": \"1\",\n" +
            "        \"po_id\": \"1\",\n" +
            "        \"po_no\": \"1\",\n" +
            "        \"pop_id\": \"211\",\n" +
            "        \"pop_qc_sub_id\": \"13\",\n" +
            "        \"pop_status\": \"1\",\n" +
            "        \"proc_name\": \"1\",\n" +
            "        \"qc_qty\": \"5\",\n" +
            "        \"qualified_qty\": \"1\",\n" +
            "        \"quality_loss\": \"1\",\n" +
            "        \"real_end_time\": \"1\",\n" +
            "        \"scrap_qty\": \"1\",\n" +
            "        \"special_qty\": \"1\",\n" +
            "        \"unusual_pic\": \"1\",\n" +
            "        \"unusual_reason\": \"1\",\n" +
            "        \"worker_name\": \"1\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    @PostMapping("/api/qm/proc_qc/modify")
    @RequiresPermissions(value = "enterprise:qm:pop_qc:create,update")
    public Object addAndUpdateProcQc(@RequestBody Map<String,Object> param,ModelMap modelMap) {
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("proc_qc_info", StringUtils.isEmpty(param.get("proc_qc_info").toString()) == true ? null : param.get("proc_qc_info"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid",StringUtils.isEmpty(param.get("uid").toString()) == true ? null : param.get("uid"));
            Map<String,Object> returnMap = procQcService.addAndUpdateProcQc(map);
            modelMap.addAttribute("description", "新增修改工序质检单成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        }catch (IllegalArgumentException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改工序质检单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E798 删除工序质检单", notes = "删除工序质检单")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "  \"token\": \"0fd4757f-4626-4f6b-8842-2b42b7af7b9f\",\n" +
            "  \"pop_qc_id\": \"1\",\n" +
            "  \"company_id\": \"\",\n" +
            "  \"client_type\": \"\",\n" +
            "  \"uid\": \"1\"\n" +
            "}")
    @PostMapping("/api/qm/proc_qc/delete")
    @RequiresPermissions(value = "enterprise:qm:pop_qc:delete")
    public Object deleteProcQc(@RequestBody Map<String,Object> param, ModelMap modelMap){
        try {

            Map<String,Object> map = new HashMap<>(16);
            map.put("pop_qc_id",param.get("pop_qc_id") == null || param.get("pop_qc_id") == "" ? null : param.get("pop_qc_id").toString().trim());
            procQcService.deleteProcQc(map);
            modelMap.addAttribute("description", "删除工序质检单成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除工序质检单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

