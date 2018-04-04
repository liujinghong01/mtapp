package com.mt.pm.production.controller;


import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pm.production.service.PmPgService;
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
* @create    2018/1/9 15:37
* @Modified By:
*/
@RestController
@Api(value = "生产小组", description = "生产小组")
public class PmPgProductionController extends BaseController {

    @Autowired
    private PmPgService pmPgService;


    @ApiOperation(value = "E689 查询生产小组列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" +
                    "{\"dep_id\":\"\"}" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\":\"5977ffb0-a93e-4a87-af44-904bb142f4d7\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg/list")
    @RequiresPermissions(value = "enterprise:pm:production_group:view")
    public Object selectProductionGroupList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType=param.get("client_type")==null? null :param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = pmPgService.selectProductionGroupList(map);
            modelMap.addAttribute("description", "查询生产小组列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产小组列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value ="B690 查询生产小组详情",notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"query\":" +
            "{\"pg_id\":\"1\"}" +
            ",\"token\":\"81a7128b-2de0-486a-b566-7ab5b951ae70\",\"uid\":\"1\"}\n" +
            "\n", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg/detail")
    @RequiresPermissions(value = "enterprise:pm:production_group:view")
    public Object selectProductionGroupDetail(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap =pmPgService.selectProductionGroupDetail(map);
            modelMap.addAttribute("description", "查询生产小组详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询生产小组详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

    @ApiOperation(value = "E507 人员删除", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"member_info\":"
                    + "{\"pg_id\":\"662\"" +
                    ",\"member_id\":\"18\"}" +
            ",\"token\":\"c724dc28-23ba-454e-81d7-e42149f2f9d1\"" +
            ",\"uid\":\"1\"}\n", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_member/delete")
    @RequiresPermissions(value = "enterprise:pm:production_group:delete")
    public Object updateMemberModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("member_info", param.get("member_info") == null ? null : param.get("member_info"));
            Map<String,Object>  returnMap=pmPgService.updateMember(map);
            modelMap.addAttribute("description", "人员删除成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        }catch(NoClassDefFoundError e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "人员删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E698 生产小组删除", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"pg_id\":\"1\"" +
            ",\"token\":\"a6c1bee7-da50-4746-9961-5324eb5d59f8\"" +
            ",\"uid\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg/delete")
    @RequiresPermissions(value = "enterprise:pm:production_group:delete")
    public Object updateProductionGroupModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("pg_info", param.get("pg_info") == null ? null : param.get("pg_info"));
            Map<String, Object> resultMap = pmPgService.updateProductionGroup(map);
            modelMap.addAttribute("description", "生产小组删除成功！");
            return setModelMap(modelMap, HttpCode.OK, resultMap);
        }catch(NoClassDefFoundError e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "生产小组删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E508 人员新增修改", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"uid\":\"71\"" +
            ",\"token\":\"3199da5c-1f01-41b8-a7d2-7e7d7fe334d3\"" +
            ",\"member_info\":" +
            "{\"member_id\":\"71\"" +
            ",\"furlough_end_date\":\"2017-12-25\"" +
            ",\"furlough_start_date\":\"2017-12-21\"" +
            ",\"is_leader\":\"0\"" +
            ",\"pg_id\":\"3\"" +
            ",\"position_code\":\"4\"" +
            ",\"position_name\":\"开发\"" +
            ",\"work_email\":\"135874121@qq.coms\"" +
            ",\"work_name\":\"lll\"" +
            ",\"work_phone\":\"135978456\"" +
            ",\"work_state\":\"1\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_member/modify")
    @RequiresPermissions(value = "enterprise:pm:production_group:create,update")
    public Object addAndUpdateMemberInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("member_info", param.get("member_info") == null ? null : param.get("member_info"));
            Map<String,Object> returnMap=pmPgService.addAndUpdateMemberInfo(map);
            modelMap.addAttribute("description", "人员新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        }catch (NoClassDefFoundError e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "人员新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E699 生产小组新增修改", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"uid\":\"71\"" +
            ",\"token\":\"94b82d57-4ad7-4f0a-a5f6-dbc1e1219112\"" +
            ",\"pg_info\":{\"assign_to_device\":\"1\"" +
            ",\"avail_status\":\"1\"" +
            ",\"calendar_id\":1" +
            ",\"calendar_name\":\"cesneir测试内容\"" +
            ",\"capa_ratio\":23" +
            ",\"day_hours\":1" +
            ",\"dep_admin\":\"开心\"" +
            ",\"dep_admin_id\":1" +
            ",\"labour_ratio\":12" +
            ",\"overload_ratio\":\"23\"" +
            ",\"parent_dep_id\":\"1\"" +
            ",\"parent_dep_name\":\"测试内容as\"" +
            ",\"pg_desc\":\"asd\"" +
            ",\"pg_id\":\"5\"" +
            ",\"pg_name\":\"设计组\"" +
            ",\"pg_type\":\"2\"" +
            ",\"queue_length\":\"20\"" +
            ",\"std_unit\":\"1\"" +
            ",\"trans_length\":\"1\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg/modify")
    @RequiresPermissions(value = "enterprise:pm:production_group:create,update")
    public Object addAndUpdateProductionGroup(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("pg_info", param.get("pg_info") == null ? null : param.get("pg_info"));
            Map<String,Object> returnMap=pmPgService.addAndUpdateProductionGroup(map);
            modelMap.addAttribute("description", "生产小组新增修改成功！");
             return setModelMap(modelMap, HttpCode.OK,returnMap);
        }catch (NullPointerException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "生产小组新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
