package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.iv.warehouse.service.MaterialStorageService;
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
@Api(value = "物料入库", description = "物料入库")
public class MaterialStorageController extends BaseController {

    @Autowired
    private MaterialStorageService materialStorageService;


    @ApiOperation(value = "E370 模糊查询单号（用于出入库）列表", notes = "模糊查询单号（用于出入库）列表")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"843c9fd7-5ff0-4bfb-b139-3c64a82e96f4\",\"company_id\":\"35\",\"query\":{\"bill_no\":\"\",\"type\":\"1\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/bill/search")
    @RequiresPermissions(value = {"enterprise:iv:in_store:view","enterprise:iv:out_store:view"})
    public Object selectBillSearch(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("query", param.get("query") == null ? null : param.get("query"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = materialStorageService.selectBillSearch(map);
            modelMap.addAttribute("description", "查询出入库列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询出入库列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E447 查询入库初始数据", notes = "查询入库初始数据")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"5ceee340-5a84-4e5c-8c7a-4be78b680f0b\",\"company_id\":\"35\",\"query\":{\"bill_no\":\"MP1111\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/bill/query_in_store_init_data")
    @RequiresPermissions(value = "enterprise:iv:in_store:view")
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
            Map<String, Object> retrunMap = materialStorageService.selectIvQueryInitData(map);
            modelMap.addAttribute("description", "查询入库初始数据成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询入库初始数据失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }





    @ApiOperation(value = "E452 查询单号所对应的出入库凭证", notes = "查询单号所对应的出入库凭证")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"5ceee340-5a84-4e5c-8c7a-4be78b680f0b\",\"company_id\":\"35\",\"query\":{\"bill_no\":\"MP1111\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/bill/query_voucher")
    @RequiresPermissions(value = "enterprise:iv:in_store:view")
    public Object selectVoucher(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = materialStorageService.selectVoucher(map);
            modelMap.addAttribute("description", "查询单号所对应的出入库凭证成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询单号所对应的出入库凭证失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }






    @ApiOperation(value = "E445 根据单号入库", notes = "根据单号入库")
    @ApiImplicitParam(name = "param", value = "{\"uid\":\"19\",\"token\":\"df010c80-9640-48b3-b38a-d2814ed82a9a\"," +
            "\"company_id\":35,\"in_store_info\":{\"dep_name\":\"\",\"dep_id\":\"\",\"bill_no\":\"CO1234561\",\"in_store_detail_list\"" +
            ":[{\"chalkup_date\":\"2017-12-07\",\"detail_id\":\"\",\"mat_desc\":\"浇口套\",\"mat_id\":1,\"mat_model\":\"1\",\"mat_no\":" +
            "\"MO20171109025\",\"mat_type_id\":29,\"mat_type_name\":\"标准件\",\"quantity\":\"90\",\"store_house_id\":2,\"store_house_name\"" +
            ":\"中央总导弹总仓库\",\"store_room_id\":2,\"store_room_name\":\"AJ跟踪导弹库位\",\"total_price\":100,\"unit\":null,\"weight\":\"12\"}]}}",
            required = true, dataType = "Map")
    @PostMapping("/api/iv/bill/in_store")
    @RequiresPermissions(value = "enterprise:iv:in_store:create")
    public Object addIvBillInStore(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("in_store_info", param.get("in_store_info") == null ? null : param.get("in_store_info"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = materialStorageService.addIvBillInStore(map);
            modelMap.addAttribute("description", "根据单号入库成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        }catch (IllegalArgumentException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "根据单号入库失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
