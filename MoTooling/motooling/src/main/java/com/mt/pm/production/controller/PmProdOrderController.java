package com.mt.pm.production.controller;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.pm.common.model.PmProdOrder;
import com.mt.pm.common.model.PmProdOrderSub;
import com.mt.pm.production.service.PmProdOrderService;
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
@Api(value="生产定单",description = "生产定单")
public class PmProdOrderController  extends BaseController {
    @Autowired
    private PmProdOrderService pmProdOrderService;

    @ApiOperation(value = "E001 查询生产定单列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"mat_id\":\"\",\n" +
            "    \"mat_name\":\"\",\n" +
            "    \"mat_no\":\"\",\n" +
            "    \"mat_type\":\"\",\n" +
            "    \"mold_no\":\"\",\n" +
            "    \"po_id\":\"\",\n" +
            "    \"po_no\":\"\",\n" +
            "    \"po_status\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_order/list")
    @RequiresPermissions(value = "enterprise:pm:routings:view")
    public Object getProdOrderListByConditions(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = pmProdOrderService.selectProdOrderByConditions(map);
            modelMap.addAttribute("description", "获取生产定单列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产定单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
    @ApiOperation(value = "E002 查询生产定单详细")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"po_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_order/detail")
    @RequiresPermissions(value = "enterprise:pm:routings:view")
    public Object getProdOrderInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("po_id")==null)
            {
                dataMap.put("prod_info", null);
            }
            else {
                Long prodId = Long.parseLong(map.get("po_id").toString());
                PmProdOrder pmProdOrder=pmProdOrderService.selectPmProdOrderByPrimaryKey(prodId);
                Map<String, Object> poMap = this.camelToUnderline(BeanToMapUtil.convertBean(pmProdOrder));
                poMap.put("prod_detail_list",this.camelToUnderline(BeanToMapUtil.convertBeanList(pmProdOrder.getProdDetailList())));
                poMap.put("po_id",poMap.get("id"));
                poMap.remove("id");
                dataMap.put("prod_info", poMap);
            }
            modelMap.addAttribute("description", "查询生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E003 添加和修改生产定单")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"proc_info\": {\n" +
            "   \"proc_pg_list\": [{\n" +
            "       \"is_default\":\"\",\n" +
            "       \"pg_id\":\"\",\n" +
            "       \"proc_id\":\"\"\n" +
            "    }],\n" +
            "    \"ass_add_time\":\"\",\n" +
            "    \"is_can_ass\":\"\",\n" +
            "    \"proc_desc\":\"\",\n" +
            "    \"id\":\"\",\n" +
            "    \"proc_name\":\"\",\n" +
            "    \"proc_no\":\"\",\n" +
            "    \"proc_type\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_order/modify")
    @RequiresPermissions({"enterprise:pm:routings:create","enterprise:pm:routings:update"})
    public Object createOrUpdateProdOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("prod_info");
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            PmProdOrder pmProdOrder=(PmProdOrder) BeanToMapUtil.convertMap(PmProdOrder.class,queryMap);
            pmProdOrder.setId(map.get("po_id")==null?null:Long.parseLong(map.get("po_id").toString()));
            pmProdOrder.setCompanyId(Long.parseLong(param.get("company_id").toString()));
            List<PmProdOrderSub> records = new ArrayList<>();
            List<Map<String, Object>> mapList = this.underlineToCamel(map.get("prod_detail_list") == null ? null : (List<Map<String, Object>>)map.get("prod_detail_list"));
            if (mapList != null) {
                for (Map<String, Object> prodMap: mapList) {
                    PmProdOrderSub pmProdOrderSub = (PmProdOrderSub)BeanToMapUtil.convertMap(PmProdOrderSub.class, prodMap);
                    pmProdOrderSub.setId(prodMap.get("procId")==null?null:Long.parseLong(prodMap.get("procId").toString()));
                    records.add(pmProdOrderSub);
                }
            }
            Long po_id = pmProdOrderService.createOrUpdateProdOrder(pmProdOrder, records);
            pmProdOrder=pmProdOrderService.selectPmProdOrderByPrimaryKey(po_id);
            Map<String, Object>dataMap = new HashMap<>();
            Map<String, Object> poMap = this.camelToUnderline(BeanToMapUtil.convertBean(pmProdOrder));
            poMap.put("po_id",poMap.get("id"));
            poMap.remove("id");
            poMap.put("prod_detail_list",this.camelToUnderline(BeanToMapUtil.convertBeanList(pmProdOrder.getProdDetailList())));
            dataMap.put("proc_info", poMap);
            modelMap.addAttribute("description", "操作生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "操作生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E004 删除生产定单")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"prod_info\": {\n" +
            "    \"po_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/prod_order/delete")
    @RequiresPermissions(value = "enterprise:pm:routings:delete")
    public Object deleteProdOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map=(Map)param.get("prod_info");
            if(map.get("po_id")==null)
            {
                logger.info("未选择删除的生产定单");
                modelMap.addAttribute("description", "删除生产定单失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            else {
                Long prodId = Long.parseLong(map.get("po_id").toString());
                pmProdOrderService.deleteProdOrderByPrimaryKey(prodId);
            }
            modelMap.addAttribute("description", "删除生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, true);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


//TODO 这个排产是否还需要？  下列controller 权限未加
//    @ApiOperation(value = "E010 排产")
//    @ApiImplicitParam(name = "param", value = "{\n" +
//            "\"client_type\": \"\",\n" +
//            "\"token\": \"\",\n" +
//            "\"uid\": \"\",\n" +
//            "\"company_id\": \"\",\n" +
//            "\"proc_info\": {\n" +
//            "   \"proc_pg_list\": [{\n" +
//            "       \"is_default\":\"\",\n" +
//            "       \"pg_id\":\"\",\n" +
//            "       \"proc_id\":\"\"\n" +
//            "    }],\n" +
//            "    \"ass_add_time\":\"\",\n" +
//            "    \"is_can_ass\":\"\",\n" +
//            "    \"proc_desc\":\"\",\n" +
//            "    \"id\":\"\",\n" +
//            "    \"proc_name\":\"\",\n" +
//            "    \"proc_no\":\"\",\n" +
//            "    \"proc_type\":\"\"\n" +
//            "    }\n" +
//            "}", required = true, dataType = "Map")
//    @PostMapping("/api/pm/schedule/start/no-use")
//    public Object schedule(@RequestBody Map<String, Object> param, ModelMap modelMap) {
//        try {
//
//            Map<String, Object>map = (Map)param.get("prod_info");
//            Map<String, Object>queryMap = new HashMap<>();
//            for (String key: map.keySet()) {
//                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
//            }
//            PmProdOrder pmProdOrder=(PmProdOrder) BeanToMapUtil.convertMap(PmProdOrder.class,queryMap);
//            pmProdOrder.setCompanyId(Long.parseLong(param.get("company_id").toString()));
//            List<PmProdOrderSub> records = new ArrayList<>();
//            List<Map<String, Object>> mapList = this.underlineToCamel(map.get("prod_detail_list") == null ? null : (List<Map<String, Object>>)map.get("prod_detail_list"));
//            if (mapList != null) {
//                for (Map<String, Object> prodMap: mapList) {
//                    PmProdOrderSub pmProdOrderSub = (PmProdOrderSub)BeanToMapUtil.convertMap(PmProdOrderSub.class, prodMap);
//                    records.add(pmProdOrderSub);
//                }
//            }
//            pmProdOrder = pmProdOrderService.schedule(pmProdOrder,records);
//            Map<String, Object>dataMap = new HashMap<>();
//            dataMap.put("proc_info", pmProdOrder);
//            modelMap.addAttribute("description", "排产，成功！");
//            return setModelMap(modelMap, HttpCode.OK, dataMap);
//        } catch (Exception ex) {
//            logger.info(ex.getMessage());
//            modelMap.addAttribute("description", "排产失败！");
//            return setModelMap(modelMap, HttpCode.FAIL);
//        }
//    }


    @ApiOperation(value = "E011 生产定单确认生成")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"po_id\": \"\",\n" +
            "\"po_no\": \"\",\n" +
            "\"proc_list\": {\n" +
            "    \"plan_end_time\":\"\",\n" +
            "    \"plan_region_load\":\"\",\n" +
            "    \"plan_start_time\":\"\",\n" +
            "    \"proc_id\":\"\",\n" +
            "    \"plan_work_time\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/po/generated")
    @RequiresPermissions(value = "enterprise:pm:routings:create")
    public Object generatedProdOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>queryMap = new HashMap<>();
            for (String key: param.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), param.get(key));
            }
            PmProdOrder pmProdOrder=(PmProdOrder) BeanToMapUtil.convertMap(PmProdOrder.class,queryMap);
            pmProdOrder.setId(Long.parseLong(queryMap.get("poId").toString()));
            pmProdOrder.setCompanyId(Long.parseLong(param.get("company_id").toString()));
            List<PmProdOrderSub> records = new ArrayList<>();
            List<Map<String, Object>> mapList = this.underlineToCamel(param.get("proc_list") == null ? null : (List<Map<String, Object>>)param.get("proc_list"));
            if (mapList != null) {
                for (Map<String, Object> prodMap: mapList) {
                    PmProdOrderSub pmProdOrderSub = (PmProdOrderSub)BeanToMapUtil.convertMap(PmProdOrderSub.class, prodMap);
                    pmProdOrderSub.setId(prodMap.get("procId")==null?null:Long.parseLong(prodMap.get("procId").toString()));
                    records.add(pmProdOrderSub);
                }
            }
            pmProdOrderService.generatedProdOrder(pmProdOrder,records);
            Map<String, Object>dataMap = new HashMap<>();
            pmProdOrder=pmProdOrderService.selectPmProdOrderByPrimaryKey(pmProdOrder.getId());
            Map<String, Object> poMap = this.camelToUnderline(BeanToMapUtil.convertBean(pmProdOrder));
            poMap.put("po_id",pmProdOrder.getId());
            poMap.remove("id");
            poMap.put("prod_detail_list",this.camelToUnderline(BeanToMapUtil.convertBeanList(pmProdOrder.getProdDetailList())));
            dataMap.put("prod_info", poMap);

            modelMap.addAttribute("description", "生成生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "生成生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"po_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/po/selectProdOrderByPBomNode")
    public Object selectProdOrderByPBomNode(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("po_id")==null)
            {
                dataMap.put("prod_info", null);
            }
            else {
                Long prodId = Long.parseLong(map.get("po_id").toString());
                List<Map<String, Object>> list=pmProdOrderService.selectProdOrderByPBomNode(prodId);

                dataMap.put("prod_info", list);
            }
            modelMap.addAttribute("description", "查询下级生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询下级生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"po_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/po/selectBomSNodes")
    public Object selectBomSNodes(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("po_id")==null)
            {
                dataMap.put("prod_info", null);
            }
            else {
                Long prodId = Long.parseLong(map.get("po_id").toString());
                List<Map<String, Object>> list=pmProdOrderService.selectBomSNodes(prodId);

                dataMap.put("prod_info", list);
            }
            modelMap.addAttribute("description", "查询下级生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询下级生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"po_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/po/selectBomPNode")
    public Object selectBomPNode(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("po_id")==null)
            {
                dataMap.put("prod_info", null);
            }
            else {
                Long prodId = Long.parseLong(map.get("po_id").toString());
                Map<String, Object> nodeMap  =pmProdOrderService.selectBomPNode(prodId);

                dataMap.put("prod_info", nodeMap);
            }
            modelMap.addAttribute("description", "查询下级生产定单，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询下级生产定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
