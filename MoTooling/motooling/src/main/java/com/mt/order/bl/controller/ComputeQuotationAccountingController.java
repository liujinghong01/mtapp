package com.mt.order.bl.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.ComputePartCostService;
import com.mt.order.bl.service.ComputeProcessCostService;
import com.mt.order.bl.service.ComputeService;
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
@Api(value = "核算单操作", description = "核算单操作")
public class ComputeQuotationAccountingController extends BaseController {

    @Autowired
    private ComputeService computeService;
    @Autowired
    private ComputePartCostService computePartCostService;
    @Autowired
    private ComputeProcessCostService computeProcessCostService;


    @ApiOperation(value = "E108 获取核算单列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"client_type\": \"1\", \"company_id\": \"1\", \"token\": \"c464002c-0857-4ddf-8d79-198a4fe39198\", " +
            "\"uid\": \"19\"," + "\"query\":{\"approval_step\":[10,20],\"compute_no\":1,\n" +
            "\"cst_company_id\":[1,2],\"cst_prod_name\":[1,2],\"type\":[1]\n" +
            "},\"curr_page\":1,\"page_size\":2}", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/list")
    @RequiresPermissions(value = "enterprise:sm:compute:view")
    public Object selectOrderList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = computeService.selectOrderList(map);
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询核算单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E111 获取核算单主要零部件明细及其子部件", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/accounting/detail/part")
    @RequiresPermissions(value = "enterprise:sm:compute:view")
    public Object selectDetailPart(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("part_id", StringUtils.isEmpty(param.get("part_id").toString()) == true ? null : Integer.valueOf(param.get("part_id").toString()));
            Map<String, Object> returnMap = computePartCostService.selectDetailPart(map);
            modelMap.addAttribute("description", "获取核算单主要零部件明细及其子部件成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取核算单主要零部件明细及其子部件失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E112 获取核算单加工项明细", notes = "")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"token\":\"e41552bb-b52c-4229-8e85-8b833bc4e506\",\n" +
            "\"compute_id\":1\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/detail/process-detail")
    @RequiresPermissions(value = "enterprise:sm:compute:view")
    public Object selectComputMold(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Long computeId = StringUtils.isEmpty(param.get("compute_id").toString()) == true ? null : Long.valueOf(param.get("compute_id").toString());
            Map<String, Object> map = computeProcessCostService.selectDetail(computeId);
            modelMap.addAttribute("description", "获取核算单加工项明细成功！");
            return setModelMap(modelMap, HttpCode.OK, map);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取核算单加工项明细失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E113 获取核算单详情(模具)", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"3e30acc2-bea0-40e6-bc90-ee7fcf20d888\", \"compute_id\":26 }", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/mold/detail")
    @RequiresPermissions(value = "enterprise:sm:compute:view")
    public Object selectComputrOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uid = param.get("uid") == null ? null : param.get("uid").toString();
            Integer compute_id = param.get("compute_id") == null ? null : Integer.valueOf(param.get("compute_id").toString());
            Map<String, Object> returnMap = computeService.selectOrderMoldDatail(compute_id);
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取核算单详情(模具)失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E115 删除核算单", notes = "")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"token\":\"e41552bb-b52c-4229-8e85-8b833bc4e506\",\n" +
            "\"compute_id\":1\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/delete")
    @RequiresPermissions(value = "enterprise:sm:compute:delete")
    public Object deleteOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Long computeId = StringUtils.isEmpty(param.get("compute_id").toString()) == true ? null : Long.valueOf(param.get("compute_id").toString());
            Boolean b = computeService.deleteOrder(computeId);
            if (b) {
                modelMap.addAttribute("description", "删除核算单成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "内部出错！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除核算单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E116 新增核算单", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"f7f5972b-4987-44da-9f04-0e4d3c359847\"," +
            "\"company_id\":\"1\",\"company_name\":\"123\",\"dep_id\":\"1\",\"dep_name\":\"1\"," +
            "\"compute_info\":{\"cst_company_id\":\"1\",\"cst_company_name\":\"你好\",\"manage_cost\":20," +
            "\"material\":\"磨具\",\"mold_cost\":\"500\",\"mold_up\":\"27000\",\"cst_products\":[{\"cavity_total\":1," +
            "\"cst_prod_name\":\"mignc\"},{\"cavity_total\":2,\"cst_prod_name\":\"3333\"}]}}", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/add")
    @RequiresPermissions(value = "enterprise:sm:compute:create,update")
    public Object addOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("compute_id", StringUtils.isEmpty(param.get("compute_id") + "") == true ? null : param.get("compute_id"));
            map.put("company_name", StringUtils.isEmpty(param.get("company_name") + "") == true ? null : param.get("company_name"));
            map.put("dep_id", StringUtils.isEmpty(param.get("dep_id") + "") == true ? null : param.get("dep_id"));
            map.put("dep_name", StringUtils.isEmpty(param.get("dep_name") + "") == true ? null : param.get("dep_name"));
            map.put("compute_info", StringUtils.isEmpty(param.get("compute_info") + "") == true ? null : param.get("compute_info"));
            Map<String, Object> mapReturn = computeService.addCustomerOrder(map);
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "新增核算单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E118 新增/修改 零部件", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"f7f5972b-4987-44da-9f04-0e4d3c359847\",\"uid\":\"1\"," +
            "\"compute_id\":\"123\",part_list\":[{\"is_detail_calc\":1,\"part_cost\":\"1\"," +
            "\"part_hardness\":1110,\"part_id\":1,\"part_name\":\"111500\"," +
            "\"sub_part_list\":[{\"is_calc_weight\":1,\"raw_nature\":\"1111s's\"},{\"is_calc_weight\":1,\"raw_nature\":\"33ss1111\"}]}," +
            "{\"is_detail_calc\":2,\"part_cost\":\"222\",\"part_hardness\":22,\"part_id\":22,\"part_name\":\"2200\"," +
            "\"sub_part_list\":[{\"is_calc_weight\":1,\"raw_nature\":\"mignc222\"},{\"is_calc_weight\":1,\"raw_nature\":\"32222\"}]}]}",
            required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/part/modify")
    @RequiresPermissions(value = "enterprise:sm:compute:create,update")
    public Object addModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("compute_id", StringUtils.isEmpty(param.get("compute_id").toString()) == true ? null : param.get("compute_id"));
            map.put("part_list", StringUtils.isEmpty(param.get("part_list") + "") == true ? null : param.get("part_list"));
            Map<String, Object> mapReturn = computePartCostService.addAndUpdateComputePartCost(map);
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增/修改零部件失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E119 删除零部件子部件信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"3e30acc2-bea0-40e6-bc90-ee7fcf20d888\",\"compute_id\":26," +
            "\"part_id\":\"\",\"sub_part_ids\":[],\"uid\":\"\"}", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/part/sub/delete")
    @RequiresPermissions(value = "enterprise:sm:compute:delete")
    public Object deletePartSub(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("part_id", StringUtils.isEmpty(param.get("part_id").toString()) == true ? null : param.get("part_id"));
            map.put("sub_part_ids", StringUtils.isEmpty(param.get("sub_part_ids").toString()) == true ? null : param.get("sub_part_ids"));
            Boolean b = computePartCostService.deletePartSub(map);
            if (b) {
                modelMap.addAttribute("description", "删除零部件子部件信息成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "内部出错！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除零部件子部件信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E121 删除零部件信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"ccb3851a-1580-4042-9dbb-73afffe9d8b1\",\"compute_id\":123,\"part_ids\":[7,6]}", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/part/delete")
    @RequiresPermissions(value = "enterprise:sm:compute:delete")
    public Object deletePart(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("compute_id", StringUtils.isEmpty(param.get("compute_id").toString()) == true ? null : param.get("compute_id"));
            map.put("part_ids", StringUtils.isEmpty(param.get("part_ids").toString()) == true ? null : param.get("part_ids"));
            Boolean b = computePartCostService.deletePart(map);
            if (b) {
                modelMap.addAttribute("description", "删除零部件信息成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "内部出错！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除零部件信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E122 新增/修改加工费明细", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/process")
    @RequiresPermissions(value = "enterprise:sm:compute:create,update")
    public Object addProcess(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("compute_id", StringUtils.isEmpty(param.get("compute_id").toString()) == true ? null : param.get("compute_id"));
            map.put("process_list", StringUtils.isEmpty(param.get("process_list").toString()) == true ? null : param.get("process_list"));
            Boolean b = computeProcessCostService.addAndUpdate(map);
            if (b) {
                modelMap.addAttribute("description", "新增修改成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "新增或修改时参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "新增/修改加工费明细失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E230 删除核算单加工项目", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/compute/processItemDel")
    @RequiresPermissions(value = "enterprise:sm:compute:delete")
    public Object delProcess(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("processItemIds", StringUtils.isEmpty(param.get("processItemIds").toString()) == true ? null : param.get("processItemIds"));
            map.put("compute_id", StringUtils.isEmpty(param.get("compute_id").toString()) == true ? null : param.get("compute_id"));
            Boolean b = computeProcessCostService.delProcess(map);
            if (b) {
                logger.info("用户" + param.get("uid") + " 删除成功");
                modelMap.addAttribute("description", "删除成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                logger.info("用户" + param.get("uid") + " 删除失败");
                modelMap.addAttribute("description", "删除时参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E138 获取核算单主要零部件列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @RequiresPermissions(value = "enterprise:sm:compute:view")
    @PostMapping("api/customerOrder/compute/getPartList")
    public Object selectGetPartList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uid = param.get("uid") == null ? null : param.get("uid").toString();
            Integer compute_id = param.get("compute_id") == null ? null : Integer.valueOf(param.get("compute_id").toString());
            Map<String, Object> returnMap = computePartCostService.selectGetPartList(compute_id);
            modelMap.addAttribute("description", "获取核算单主要零部件列表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取核算单主要零部件列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E165 参考历史核算单生成新核算单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @RequiresPermissions(value = "enterprise:sm:compute:create")
    @PostMapping("api/customerOrder/compute/associate")
    public Object selectassciate(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("compute_id", param.get("compute_id") == null ? null : param.get("compute_id"));
            map.put("current_compute_id", param.get("current_compute_id") == null || param.get("current_compute_id") == "" ? null : param.get("current_compute_id"));
            map.put("uid", param.get("uid") == null ? null : Integer.valueOf(param.get("uid").toString()));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> mapReturn = computeService.selectassciate(map);
            modelMap.addAttribute("description", "参考历史核算单生成新核算单成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "参考历史核算单生成新核算单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
