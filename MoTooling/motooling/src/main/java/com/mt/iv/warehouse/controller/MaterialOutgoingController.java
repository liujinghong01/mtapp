package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.iv.warehouse.service.MaterialOutgoingService;
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
@Api(value = "物料出库", description = "物料出库")
public class MaterialOutgoingController extends BaseController {

    @Autowired
    private MaterialOutgoingService materialOutgoingService;

    @ApiOperation(value = "E447 查询出库初始数据", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/bill/query_out_store_init_data")
    @RequiresPermissions(value = "enterprise:iv:out_store:view")
    public Object selectIvQueryInitData(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String bill_no =null;
            if(!param.containsKey("query")){
                modelMap.addAttribute("description", "缺少参数！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            else {
                Map<String, Object> query = (Map<String, Object>) param.get("query");
                if (query.get("bill_no") == null || query.get("bill_no") == "") {
                    modelMap.addAttribute("description", "请传单号！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
                bill_no = query.get("bill_no").toString();
            }
            Map<String, Object> map = new HashMap();
            map.put("bill_no", bill_no);
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = materialOutgoingService.selectIvQueryInitData(map);
            modelMap.addAttribute("description", "查询出库初始数据成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询出库初始数据失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E455 根据单号出库", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"uid\":\"19\",\"token\":\"e765ff35-ed6c-49c7-b7bd-307b7afb847f\"," +
            "\"company_id\":35,\"out_store_info\":{\"dep_name\":\"\",\"dep_id\":\"\",\"bill_no\":\"CO1234561\",\"out_store_detail_list\":" +
            "[{\"chalkup_date\":\"2017-12-07\",\"detail_id\":\"\",\"mat_desc\":\"浇口套\",\"mat_id\":1,\"mat_model\":\"1\",\"mat_no\":" +
            "\"MO20171109025\",\"mat_type_id\":29,\"mat_type_name\":\"标准件\",\"quantity\":90,\"store_house_id\":1,\"store_house_name\":\"仓库1\"," +
            "\"store_room_id\":1,\"store_room_name\":\"库位1\",\"total_price\":500,\"unit\":null,\"weight\":\"12\"}]}}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/bill/out_store")
    @RequiresPermissions(value = "enterprise:iv:out_store:create")
    public Object addIvBillInStore(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("out_store_info", param.get("out_store_info") == null ? null : param.get("out_store_info"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = materialOutgoingService.addIvBillInStore(map);
            modelMap.addAttribute("description", "根据单号出库成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        }catch (IllegalArgumentException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "根据单号出库失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }








}
