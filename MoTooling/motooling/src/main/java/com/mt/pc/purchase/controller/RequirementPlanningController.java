package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.RequirementPlanningService;
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
@Api(value = "物料需求计划", description = "物料需求计划")
public class RequirementPlanningController extends BaseController {

    @Autowired
    private RequirementPlanningService requirementPlanningService;


    @ApiOperation(value = "E231 查询物料需求计划列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"8f959b5b-9485-4b69-969f-33fed08ee6f2\",\n" +
            "\"company_id\": \"35\", \"query\":{\"create_type\":\"\",\"mat_desc\":\"\"},\"curr_page\":1,\n" +
            " \"page_size\":2}", required = true, dataType = "Map")
    @PostMapping("/api/pc/mat_require_plan/list")
    @RequiresPermissions(value = "enterprise:pc:mat_require_plan:view")
    public Object selectRequirePlanList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = requirementPlanningService.selectRequirePlanList(map);
            modelMap.addAttribute("description", "查询物料需求计划列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料需求计划列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E277 查询物料需求详情", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"8f959b5b-9485-4b69-969f-33fed08ee6f2\",\n" +
            "\"company_id\": \"35\", \"query\":{\"require_id\":\"1\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pc/mat_require_plan/detail")
    @RequiresPermissions(value = "enterprise:pc:mat_require_plan:view")
    public Object selectDetail(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = requirementPlanningService.selectRequirePlanDeatil(map);
            modelMap.addAttribute("description", "查询物料需求详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料需求详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E241 需求计划删除", notes = "")
    @ApiImplicitParam(name = "param", value = "{ \"token\":\"8f959b5b-9485-4b69-969f-33fed08ee6f2\",\n" +
            "\"company_id\": \"35\", \"require_id\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/pc/mat_require_plan/delete")
    @RequiresPermissions(value = "enterprise:pc:mat_require_plan:delete")
    public Object deleteRequire(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Integer requireId =StringUtils.isEmpty(param.get("require_id").toString()) == true ? null : Integer.valueOf(param.get("require_id").toString());
            boolean b = requirementPlanningService.deleteRequire(requireId);
            modelMap.addAttribute("description", "删除成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    }
