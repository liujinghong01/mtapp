package com.mt.bm.bom.controller;

import com.mt.bm.bom.service.CascadeMatService;
import com.mt.bm.common.model.CascadeMat;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "项目产品分类和物料关联表操作", description = "项目产品分类和物料关联表操作")
public class CascadeMatController extends BaseController {
    @Autowired
    private CascadeMatService cascadeMatService;


//    @ApiOperation(value = "E001 记录删除", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号]" +
//            "[token  用户标识  string  必须] [uid  用户uid  number  必须]  [id  id 必填] ")
//    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\",\"uid\":\"\",\"token\":\"\",\"id\":\"35\"}", required = true, dataType = "Map")
//    @PostMapping("/api/CascadeMat/delete")
    public Object deleteCascadeMat(@RequestBody Map<String, Object> param,ModelMap modelMap) {
        try {
        
            int id= Integer.parseInt(param.get("id").toString());
            int b = cascadeMatService.deleteCascadeMat(id);
            if (b>0) {
                modelMap.addAttribute("description", "数据删除，成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "删除失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

//    @ApiOperation(value = "E002 记录增加", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号]" +
//            "[token  用户标识  string  必须] [uid  用户uid  number  必须]  [prod_type  产品 string 必须] "+
//            "  [mat_type  关联物料类型 string 必须]   [mat_cascade_name  关联物料 string 必须]   "+
//            "[mat_cascade_no  编码 string 必须] [remark  备注 string ]")
//    @ApiImplicitParam(name = "param", value = "{\n" +
//            "\"client_type\":\"\",\n" +
//            "\"token\":\"\",\n" +
//            "\"uid\":\"\"\n" +
//            "\"prod_type\":\"\",\n" +
//            "\"mat_type\":\"\",\n" +
//            "\"mat_cascade_name\":\"\",\n" +
//            "\"mat_cascade_no\":\"\",\n" +
//            "\"remark\":\"\",\n" +
//            "}", required = true, dataType = "Map")
//    @PostMapping("/api/CascadeMat/add")
    public Object addCascadeMat(@RequestBody Map<String, Object> param,ModelMap modelMap) {
        try {
        
            CascadeMat cascadeMat=new CascadeMat();
            cascadeMat.setProdType(param.get("prod_type").toString());
            cascadeMat.setMatType(param.get("mat_type").toString());
            cascadeMat.setMatCascadeName( param.get("mat_cascade_name").toString());
            cascadeMat.setMatCascadeNo(param.get("mat_cascade_no").toString());
            cascadeMat.setRemark( param.get("remark").toString());
            cascadeMat.setHandlerId( param.get("uid").toString());
            cascadeMat.setHandledTime(new Date());

            int b = cascadeMatService.insertCascadeMat(cascadeMat);
            if (b>0) {
                modelMap.addAttribute("description", "数据增加，成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "增加失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "增加失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

    @ApiOperation(value = "E002 获取物料类型列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"company_id\": \"\",\n"+
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/cascade_type/list")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:view")
    public Object getCascadeMatTypeList( @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object> map = new HashMap<>();
            String cid=param.get("company_id").toString();
            map.put("company_id",cid);
            List<String> CascadeMatlist = cascadeMatService.selectCascadeMatTypeList(map);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("cascade_type_list", CascadeMatlist);
            modelMap.addAttribute("description", "获取类型列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "获取类型列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E003 获取物料类型下所有级联名称")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n"+
            "\"query\": {\n" +
            "    \"mat_type\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/cascade_mat/list")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:view")
    public Object getCascadeMatByMatType( @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            String cid=param.get("company_id").toString();
            map.put("company_id",cid);
            String matType=map.get("mat_type").toString().trim();
            List<CascadeMat> listCascadeMat;
            if (matType.length() < 1 ) {
                listCascadeMat=null;
            }
            listCascadeMat = cascadeMatService.selectByMatType(map);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("listCascadeMat", listCascadeMat);
            modelMap.addAttribute("description", "获取该类型所有级联名称，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "获取该类型所有级联名称，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
