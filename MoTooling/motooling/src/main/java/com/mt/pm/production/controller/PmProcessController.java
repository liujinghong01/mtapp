package com.mt.pm.production.controller;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.pm.common.model.*;
import com.mt.pm.production.service.PmProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value="工序",description = "工序")
public class PmProcessController extends BaseController {
    @Autowired
    private PmProcessService pmProcessService;

    @ApiOperation(value = "E001 查询基础工序列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"proc_name\":\"\",\n" +
            "    \"proc_no\":\"\",\n" +
            "    \"status\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/proc/list")
    @RequiresPermissions(value = "enterprise:pm:working_procedure:view")
    public Object getPmProcessListByConditions(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("query", param.get("query") == null || param.get("query").toString().isEmpty() ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = pmProcessService.selectProcessByConditions(map);
            modelMap.addAttribute("description", "获取基础工序列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询基础工序列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
    @ApiOperation(value = "E002 查询基础工序详细")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"proc_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/proc/detail")
    @RequiresPermissions(value = "enterprise:pm:working_procedure:view")
    public Object getPmProcessInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
 
            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("proc_id")==null)
            {
                dataMap.put("proc_info", null);
            }
            else {
                Long procId = Long.parseLong(map.get("proc_id").toString());
                PmProcConf pmProcConf=pmProcessService.selectProcessByPrimaryKey(procId);
                Map<String, Object> confMap = this.camelToUnderline(BeanToMapUtil.convertBean(pmProcConf));
                dataMap.put("proc_info", confMap);
            }
            modelMap.addAttribute("description", "查询基础工序，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询基础工序失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E003 添加和修改基础工序")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"proc_info\": {\n" +
            "   \"pg_list\": [{\n" +
            "       \"is_default\":\"\",\n" +
            "       \"pg_id\":\"\",\n" +
            "       \"proc_id\":\"\"\n" +
            "    }],\n" +
            "    \"other_time\":\"\",\n" +
            "    \"ass_unit\":\"\",\n" +
            "    \"is_can_ass\":\"\",\n" +
            "    \"proc_desc\":\"\",\n" +
            "    \"id\":\"\",\n" +
            "    \"proc_name\":\"\",\n" +
            "    \"proc_no\":\"\",\n" +
            "    \"status\":\"\",\n" +
            "    \"proc_type\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/proc/modify")
    @RequiresPermissions(value = "enterprise:pm:working_procedure:create,update")
    public Object createOrUpdatePmProcess(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Map<String, Object>map = (Map)param.get("proc_info");
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            PmProcConf pmProcConf=(PmProcConf) BeanToMapUtil.convertMap(PmProcConf.class,queryMap);
            pmProcConf.setCompanyId(param.get("company_id").toString());
            List<PmProcPg> records = new ArrayList<>();
            List<Map<String, Object>> mapList = this.underlineToCamel(map.get("pg_list") == null ? null : (List<Map<String, Object>>)map.get("pg_list"));
            if (mapList != null) {
                for (Map<String, Object> prodMap: mapList) {
                    PmProcPg quoteMoldProd = (PmProcPg)BeanToMapUtil.convertMap(PmProcPg.class, prodMap);
                    records.add(quoteMoldProd);
                }
            }
            boolean result = pmProcessService.createOrUpdateProcess(pmProcConf,records);
            pmProcConf=pmProcessService.selectProcessByPrimaryKey(pmProcConf.getId());
            Map<String, Object> confMap = this.camelToUnderline(BeanToMapUtil.convertBean(pmProcConf));
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("proc_info", confMap);
            modelMap.addAttribute("description", "操作基础工序，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "操作基础工序失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E004 删除基础工序")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"proc_info\": {\n" +
            "    \"proc_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/proc/delete")
    @RequiresPermissions(value = "enterprise:pm:working_procedure:delete")
    public Object deletePmProcess(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Map<String, Object> map=(Map)param.get("proc_info");
            if(map.get("proc_id")==null)
            {
                logger.info("未选择删除的工序");
                modelMap.addAttribute("description", "删除基础工序失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            else {
                Long procId = Long.parseLong(map.get("proc_id").toString());
                pmProcessService.deleteProcessByPrimaryKey(procId);
            }
            modelMap.addAttribute("description", "删除基础工序，成功！");
            return setModelMap(modelMap, HttpCode.OK, true);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除基础工序失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }







    @ApiOperation(value = "E011 查询工艺模板种类列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"query\": {\n" +
            "    \"tmpl_type_name\":\"\"\n" +
            "    },\n" +
            "\"company_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl_type/list")
    @RequiresPermissions(value = "enterprise:pm:technology_template:view")
    public Object getPmRoutingTmplTypeList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object>map = (Map)param.get("query");
            String cid=param.get("company_id").toString();
            map.put("companyId",cid);
            List<Map<String, Object>> listType;
            if(cid.equals(""))
            {
                listType=null;
            }
            else {
                listType = pmProcessService.selectRoutingTmplTypeList(map);
            }
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("routing_tmpl_type_list", listType);
            modelMap.addAttribute("description", "获取工艺模板种类列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取工艺模板种类列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E013 编辑工艺模板种类")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"routing_tmpl_type_info\": {\n" +
            "    \"tmpl_type_name\":\"\",\n" +
            "    \"status\":\"\"\n" +
            "    },\n" +
            "\"company_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl_type/edit")
    @RequiresPermissions(value = "enterprise:pm:technology_template:update,create")
    public Object createOrUpdatePmRoutingTmplType(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object>map = (Map)param.get("routing_tmpl_type_info");
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            PmRoutingTmplType type=(PmRoutingTmplType) BeanToMapUtil.convertMap(PmRoutingTmplType.class,queryMap);
            type.setCompanyId(param.get("company_id").toString());
            type.setLastHandlerId(Long.parseLong(param.get("uid").toString()));
            pmProcessService.createOrUpdatePmRoutingTmplType(type);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("routing_tmpl_type_info", type);
            modelMap.addAttribute("description", "编辑工艺模板种类，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "编辑工艺模板种类，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E014 删除工艺模板种类")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"type_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl_type/delete")
    @RequiresPermissions(value = "enterprise:pm:technology_template:delete")
    public Object deletePmRoutingTmplType(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            boolean result=true;
            if(param.get("type_id")==null)
            {
                modelMap.addAttribute("description", "未选择要删除的工艺模板类型");
                return setModelMap(modelMap, HttpCode.FAIL, false);
            }
            else {
                Long typeId = Long.parseLong(param.get("type_id").toString()) ;
                result=pmProcessService.deleteRoutingTmplTypeByPrimaryKey(typeId);
            }
            if(result) {
                modelMap.addAttribute("description", "删除工艺模板种类，成功！");
                return setModelMap(modelMap, HttpCode.OK, true);
            }
            else
            {
                modelMap.addAttribute("description", "工艺模板种类已被引用，不能删除！");
                return setModelMap(modelMap, HttpCode.OK, false);
            }

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除工艺模板种类，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }




    @ApiOperation(value = "E012 查询工艺模板种类列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"tmpl_type_name\":\"\",\n" +
            "    \"status\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl_type/list2")
    @RequiresPermissions(value = "enterprise:pm:technology_template:view")
    public Object getPmRoutingTmplTypeListByConditions(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("query", param.get("query") == null || param.get("query").toString().isEmpty() ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = pmProcessService.selectRoutingTmplTypeByConditions(map);
            modelMap.addAttribute("description", "获取工艺模板种类列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询基工艺模板种类列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }




    @ApiOperation(value = "E021 查询工艺模板列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"tmpl_name\":\"\",\n" +
            "    \"tmpl_type_id\":\"\",\n" +
            "    \"tmpl_type_name\":\"\",\n" +
            "    \"status\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl/list")
    @RequiresPermissions(value = "enterprise:pm:technology_template:view")
    public Object getPmRoutingTmplListByConditions(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("query", param.get("query") == null || param.get("query").toString().isEmpty() ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = pmProcessService.selectRoutingTmplByConditions(map);
            modelMap.addAttribute("description", "获取工艺模板列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询基工艺模板列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E022 删除工艺模板")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"proc_info\": {\n" +
            "    \"tmpl_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl/delete")
    @RequiresPermissions(value = "enterprise:pm:technology_template:delete")
    public Object deletePmRoutingTmpl(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map=(Map)param.get("proc_info");
            boolean result=true;
            if(map.get("tmpl_id")==null)
            {
                modelMap.addAttribute("description", "未选择要删除的工艺模板");
                return setModelMap(modelMap, HttpCode.FAIL, false);
            }
            else {
                Long tmplId = Long.parseLong(map.get("tmpl_id").toString()) ;
                result=pmProcessService.deleteRoutingTmplByPrimaryKey(tmplId);
            }
            modelMap.addAttribute("description", "删除工艺模板，成功！");
            return setModelMap(modelMap, HttpCode.OK, true);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除工艺模板，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E023 编辑工艺模板")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"routing_tmpl_info\": {\n" +
            "    \"proc_list\": [{\n" +
            "       \"tmpl_sub_id\":\"\",\n" +
            "       \"proc_type\":\"\",\n" +
            "       \"proc_seq\":\"\",\n" +
            "       \"proc_name\":\"\",\n" +
            "       \"proc_id\":\"\",\n" +
            "       \"proc_desc\":\"\",\n" +
            "       \"pg_id\":\"\",\n" +
            "       \"device_id\":\"\",\n" +
            "       \"other_time\":\"\",\n" +
            "       \"machine_time\":\"\",\n" +
            "       \"labor_time\":\"\",\n" +
            "       \"is_need_qc\":\"\",\n" +
            "       \"ass_unit\":\"\",\n" +
            "       \"ass_calc_qty\":\"\",\n" +
            "       \"ass_up\":\"\",\n" +
            "       \"ass_time\":\"\",\n" +
            "       \"proc_qty\":\"\",\n" +
            "       \"ass_price\":\"\",\n" +
            "       \"pic_path\":\"\"\n" +
            "    }],\n" +
            "    \"id\":\"\",\n" +
            "    \"tmpl_name\":\"\",\n" +
            "    \"tmpl_type_id\":\"\",\n" +
            "    \"tmpl_type_name\":\"\",\n" +
            "    \"tmpl_desc\":\"\",\n" +
            "    \"remark\":\"\"\n" +
            "    },\n" +
            "\"company_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl/modify")
    @RequiresPermissions(value = "enterprise:pm:technology_template:create,update")
    public Object createOrUpdatePmRoutingTmpl(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("routing_tmpl_info");
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            PmRoutingTmpl tmpl=(PmRoutingTmpl) BeanToMapUtil.convertMap(PmRoutingTmpl.class,queryMap);
            tmpl.setCompanyId(param.get("company_id").toString());
            List<PmRoutingTmplSub> records = new ArrayList<>();
            List<Map<String, Object>> mapList = this.underlineToCamel(map.get("sub_list") == null ? null : (List<Map<String, Object>>)map.get("sub_list"));
            if (mapList != null) {
                for (Map<String, Object> prodMap: mapList) {
                    PmRoutingTmplSub sub = (PmRoutingTmplSub)BeanToMapUtil.convertMap(PmRoutingTmplSub.class, prodMap);
                    records.add(sub);
                }
            }
            pmProcessService.createOrUpdatePmRoutingTmpl(tmpl,records);
            tmpl=pmProcessService.selectRoutingTmplByPrimaryKey(tmpl.getId());
            Map<String, Object>dataMap = new HashMap<>();
            Map<String, Object> confMap = this.camelToUnderline(BeanToMapUtil.convertBean(tmpl));
            dataMap.put("routing_tmpl_info", confMap);
            modelMap.addAttribute("description", "编辑工艺模板，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "编辑工艺模板，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E024 查询工艺模板详细")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"tmpl_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/routing_tmpl/detail")
    @RequiresPermissions(value = "enterprise:pm:technology_template:view")
    public Object getPmRoutingTmplInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("tmpl_id")==null)
            {
                dataMap.put("tmpl_id", null);
            }
            else {
                Long tmplId = Long.parseLong(map.get("tmpl_id").toString());
                PmRoutingTmpl tmpl = pmProcessService.selectRoutingTmplByPrimaryKey(tmplId);
                Map<String, Object> tmplMap = this.camelToUnderline(BeanToMapUtil.convertBean(tmpl));
                dataMap.put("routing_tmpl_info",tmplMap);
            }
            modelMap.addAttribute("description", "查询工艺模板详细，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询工艺模板详细失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
