package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.iv.warehouse.service.VoucherService;
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
@Api(value="凭证管理",description = "凭证管理")
public class VoucherController extends BaseController{

    @Autowired
    private VoucherService voucherService;


    /**
     * 查询凭证管理列表
     * @param param
     * @param modelMap
     * @return
     */
    @ApiOperation(value="E571 查询凭证管理列表",notes = "")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"35\",\n" +
            "  \"curr_page\": 1,\n" +
            "  \"page_size\": 5,\n" +
            "  \"uid\": \"97\",\n" +
            "  \"inquiry_id\": \"1\",\n" +
            "  \"query\": {\n" +
            "    \"voucher_no\": \"\",\n" +
            "    \"voucher_type\": 4\n" +
            "  },\n" +
            "  \"token\": \"d8034ce6-f8d5-4794-82d9-6f8f412af7bd\"\n" +
            "}")
    @PostMapping("/api/iv/voucher/list")
    @RequiresPermissions(value = "enterprise:iv:voucher_list:view")
    public Object selectVoucherList(@RequestBody Map<String,Object> param, ModelMap modelMap){
        try {
                String cToken = param.get("token") == null ? null : param.get("token").toString().trim();
                if (!this.isLoginUser(cToken)) {
                    modelMap.addAttribute("description", "该用户未登录，失败！");
                    return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
                }
            Map<String,Object> map = new HashMap<>();
            map.put("company_id" ,param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            Map<String,Object> returnMap = voucherService.selectVoucherList(map);
            modelMap.addAttribute("description", "查询凭证管理列表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (IllegalArgumentException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询凭证管理列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    /**
     * 查询凭证管理详情
     * @param param
     * @param modelMap
     * @return
     */
    @ApiOperation(value="E572 查询凭证管理详情", notes = "")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"35\",\n" +
            "  \"uid\": \"97\",\n" +
            "  \"query\": {\n" +
            "    \"voucher_no\": \"PZ17120447\"\n" +
            "  },\n" +
            "  \"token\": \"264cfdf3-9077-4ba4-8a15-9e77121653b7\"\n" +
            "}")
    @PostMapping("/api/iv/voucher/detail")
    @RequiresPermissions(value = "enterprise:iv:voucher_list:view")
    public Object selectVoucherDetail(@RequestBody Map<String,Object> param,ModelMap modelMap){
        try {

            Map<String,Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            map.put("company_id",param.get("company_id") == null ? null : param.get("company_id"));
            Map<String,Object> returnMap = voucherService.selectVoucherDetail(map);
            modelMap.addAttribute("description", "查询凭证管理详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询凭证管理详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E761 查询凭证单中某物料信息", notes = "")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"uid\": \"137\",\n" +
            "  \"query\": {\n" +
            "    \"detail_id\": 12\n" +
            "  },\n" +
            "  \"token\": \"dcbd9b22-311a-46dd-93ce-1226e9debedd\"\n" +
            "}")
    @PostMapping("/api/iv/voucher/one_detail")
    @RequiresPermissions(value = "enterprise:iv:voucher_list:view")
    public Object selectVoucherOneDetail(@RequestBody Map<String,Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> returnMap = voucherService.selectVoucherOneDetail(map);
            modelMap.addAttribute("description", "查询凭证单中某物料信息成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询凭证单中某物料信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
