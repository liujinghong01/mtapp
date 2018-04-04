package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.iv.warehouse.service.StoreDirectChangeService;
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
* @create    2018/1/26 15:11
* @Modified By:
*/    
@RestController
@Api(value = "库存物料管理",description = "库存物料管理")
public class StoreDirectChangeController extends BaseController {

    @Autowired
    private StoreDirectChangeService  storeDirectChangeService;

    @ApiOperation(value = "E428 库存物料管理", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"company_id\":\"35\"" +
            ",\"uid\":\"2\"" +
            ",\"client_type\":\"\"" +
            ",\"token\":\"73b9608c-6806-4501-ac89-efd9eaf5c33f\"" +
            ",\"company_name\":\"伟博思\"" +
            ",\"change_info\":{\"change_id\":4" +
            ",\"change_no\":\"DS001\"" +
            ",\"change_type\":\"1\"" +
            ",\"create_time\":\"2017-09-11\"" +
            ",\"creator_id\":1" +
            ",\"in_store_house_id\":\"1\"" +
            ",\"in_store_house_name\":\"仓库1\"" +
            ",\"out_store_house_id\":\"1\"" +
            ",\"out_store_house_name\":\"仓库1\"" +
            ",\"remark\":\"直接入库 \"" +
            ",\"total_price\":\"200\"" +
            ",\"total_qty\":\"20\"" +
            ",\"total_weight\":\"56\"" +
            ",\"change_detail_list\":" +
            "[{\"change_detail_id\":4" +
            ",\"deal_flag\":1" +
            ",\"in_store_room_id\":3" +
            ",\"in_store_room_name\":\"库位3\"" +
            ",\"out_store_room_id\":3" +
            ",\"out_store_room_name\":\"库位3\"" +
            ",\"mat_desc\":\"asd\"" +
            ",\"mat_id\":\"1\"" +
            ",\"mat_model\":\"HKJL\"" +
            ",\"mat_no\":\"S001\"" +
            ",\"mat_type_id\":1" +
            ",\"mat_type_name\":\"测试内容xo8i\"" +
            ",\"quantity\":10" +
            ",\"weight\":56" +
            ",\"price\":\"100\"" +
            ",\"unit\":\"assa\"}]}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_mat/direct_change")
    @RequiresPermissions(value = "enterprise:iv:out_in_store:create,update")
    public Object storeDirectChangeManager(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("change_info", param.get("change_info") == null ? null : param.get("change_info"));
            Map<String, Object> retrunMap = storeDirectChangeService.storeDirectChangeManager(map);
            modelMap.addAttribute("description", "库存物料管理执行成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
         }catch (IllegalArgumentException ex){
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
         }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "库存物料管理执行失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
