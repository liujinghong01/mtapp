package com.mt.bm.bom.controller;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.mt.bm.bom.service.BomService;
import com.mt.bm.bom.service.MatService;
import com.mt.bm.common.model.Bom;
import com.mt.bm.common.model.BomNode;
import com.mt.bm.common.model.Mat;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
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
@Api(value = "BOM操作", description = "BOM操作")
public class BomController extends BaseController {
    @Autowired
    private BomService bomService;
    @Autowired
    private MatService matService;

  /*
        @ApiOperation(value = "E002 创建BOM", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号]" +
            "[token  用户标识  string  必须] [uid  用户uid  number  必须]  [mold_no  模号 string 必须] "+
            "  [is_crt_bom  订单下达-制定模具BOM string 必须] ")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"mold_no\":\"\",\n" +
            "\"is_crt_bom\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom/update")
    public Object createBom(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
        
            String is_crt_bom;String mold_no;String uid;String companyId;
            is_crt_bom=param.get("is_crt_bom").toString();
            mold_no=param.get("mold_no").toString();
            uid=param.get("uid").toString();
            companyId=param.get("company_id").toString();
            boolean bomNodeId = bomService.createBom(is_crt_bom, mold_no,uid,companyId,1);
            if (bomNodeId) {
                modelMap.addAttribute("description", "创建BOM，成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "创建BOM失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "创建BOM失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

*/

    @ApiOperation(value = "E006 查询BOM节点详细")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"node_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom_node/info")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:view","enterprise:bm:electrode_bom:view"},logical = Logical.OR)
    public Object getBomNodeInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
        

            Map<String, Object> dataMap = new HashMap<String, Object>();
            long nodeId = Long.parseLong(param.get("node_id").toString());
            BomNode bomNode=bomService.selectBomNode(nodeId);
            Map<String, Object> nodeMap = this.camelToUnderline(BeanToMapUtil.convertBean(bomNode));
            nodeMap.put("mat",this.camelToUnderline(BeanToMapUtil.convertBean(bomNode.getMat())));
            dataMap.put("bom_node_info", nodeMap);

            modelMap.addAttribute("description", "查询BOM节点，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询BOM节点失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E005 查询BOM节点列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"query\": {\n" +
            "    \"bom_id\":\"\",\n" +
            "    \"node_id\":\"\",\n" +
            "    \"mat_type\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom_node/list")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:view","enterprise:bm:electrode_bom:view"},logical = Logical.OR)
    public Object getBomNodes(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
        

            Map<String, Object>map = (Map)param.get("query");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(map.get("mat_type").toString().equals("电极"))
            {
                BomNode bomNode=bomService.selectBomNode(Long.parseLong(map.get("node_id").toString()));
                if(bomNode.getPath().equals("0")||bomNode.getParentPath().equals(""))
                {
                    dataMap.put("bom_node_list", bomService.selectEBomNodes(map));
                }
                else
                {
                    dataMap.put("bom_node_list", bomService.selectBomNodes(map));
                }
            }
            else {
                dataMap.put("bom_node_list", bomService.selectBomNodes(map));
            }

            modelMap.addAttribute("description", "查询BOM节点列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询BOM节点列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E007 删除BOM节点列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"node_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom_node/delete")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:delete","enterprise:bm:electrode_bom:delete"},logical = Logical.OR)
    public Object deleteBomNode(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
        

            Long nodeId = Long.parseLong(param.get("node_id").toString());
            bomService.deleteBomNode(nodeId);

            modelMap.addAttribute("description", "删除BOM节点，成功！");
            return setModelMap(modelMap, HttpCode.OK, true);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除BOM节点失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E004 添加和修改BOM节点")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"bom_id\": \"\",\n" +
            "\"p_node_id\": \"\",\n" +
            "\"node_info\": {\n" +
            "    \"node_id\":\"\",\n" +
            "    \"mat_id\":\"\",\n" +
            "    \"mat_no\":\"\",\n" +
            "    \"mat_type\":\"\",\n" +
            "    \"is_purchase\":\"\",\n" +
            "    \"total_qty\":\"\",\n" +
            "    \"remark\":\"\",\n" +
            "    \"processes_r_count\":\"\",\n" +
            "    \"processes_z_count\":\"\",\n" +
            "    \"processes_f_count\":\"\"\n" +
            "    },\n" +
            "\"mat_info\":{\n" +
            "    \"mat_id\":\"\",\n" +
            "    \"mat_name_id\":\"\",\n" +
            "    \"mat_no\":\"\",\n" +
            "    \"mat_name\":\"\",\n" +
            "    \"mat_type_id\":\"\",\n" +
            "    \"mat_type_name\":\"\",\n" +
            "    \"rawnature_id\":\"\",\n" +
            "    \"rawnature_name\":\"\",\n" +
            "    \"procure_style\":\"\",\n" +
            "    \"spec\":\"\",\n" +
            "    \"shape\":\"\",\n" +
            "    \"length\":\"\",\n" +
            "    \"width\":\"\",\n" +
            "    \"height\":\"\",\n" +
            "    \"weight\":\"\",\n" +
            "    \"plan_price\":\"\",\n" +
            "    \"pic_path\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom_node/update")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:create,update","enterprise:bm:electrode_bom:create,update"},logical = Logical.OR)
    public Object createOrUpdateBomNode(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
        
            Long pNodeId= null;
            if (param.get("p_node_id")!=null&&!param.get("p_node_id").toString().equals("")) {
                pNodeId = Long.parseLong(param.get("p_node_id").toString());
            }
            Map<String, Object>map = (Map)param.get("node_info");
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            BomNode bomNode=(BomNode) BeanToMapUtil.convertMap(BomNode.class,queryMap);
            if ("".equals(bomNode.getMatType())) {
                modelMap.addAttribute("description", "BOM节点类型未填写！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map = (Map)param.get("mat_info");
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            Mat mat=(Mat) BeanToMapUtil.convertMap(Mat.class,queryMap);
            if (mat.getMatTypeId()==null||"".equals(mat.getMatTypeId().toString())) {
                modelMap.addAttribute("description", "BOM节点类型未填写！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            mat.setCompanyId(param.get("company_id").toString());
            mat.setCompanyName(param.get("company_id").toString());
            boolean result = bomService.createOrUpdateBomNode(pNodeId,bomNode,mat);

            modelMap.addAttribute("description", "编辑BOM节点，成功！");
            return setModelMap(modelMap, HttpCode.OK, true);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "编辑BOM节点失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E003 获取各种状态下的Bom列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\": {\n" +
            "    \"app_flag\":\"\",\n" +
            "    \"cst_name\":\"\",\n" +
            "    \"prod_name\":\"\"\n" +
            "    },\n" +
            "\"curr_page\": \"\",\n" +
            "\"page_size\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom/list")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:view","enterprise:bm:electrode_bom:view"},logical = Logical.OR)
    public Object getBomList( @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
        
            Map<String, Object>map = (Map)param.get("query");
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            map.put("query", param.get("query") == null || param.get("query").toString().isEmpty() ? null : param.get("query"));
            map.put("company_id", param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size", param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? 10 :  Integer.valueOf(param.get("page_size").toString()));
            Map<String, Object> boms = bomService.selectBomByConditions(map);
                //这是返回的分页类 上面 MAP 自写
                //Pagination pagination = bomService.selectBomByConditions(map);
            modelMap.addAttribute("description", "获取Bom列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, boms);

        } catch (Exception ex){
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "获取Bom列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E011 获取物料名称列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/mat_name/list")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:view","enterprise:bm:electrode_bom:view"},logical = Logical.OR)
    public Object selectMatNameList( @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object>map = new HashMap<>();
            map.put("company_id", param.get("company_id").toString());
            List<String> atlist = matService.selectMatNameList(map);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("mat_name_list", atlist);
            modelMap.addAttribute("description", "获取物料名称列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "获取物料名称列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E012 获取物料名称下所有级联规格型号")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"query\":{\n" +
            "    \"mat_name\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/mat_name_spec/list")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:view","enterprise:bm:electrode_bom:view"},logical = Logical.OR)
    public Object getCascadeMatByMatType( @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object>map = (Map)param.get("query");
            map.put("company_id", param.get("company_id").toString());
            List<Map<String, Object>> matNameSpec;
            if (map.get("mat_name").toString().length() < 1 ) {
                matNameSpec=null;
            }
            matNameSpec = matService.selectMatNameSpecList(map);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("mat_info_list", matNameSpec);
            modelMap.addAttribute("description", "获取该类型所有级联名称，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "获取该类型所有级联名称，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E009  BOM审批")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"bom_info\":{\n" +
            "    \"mold_no\":\"\",\n" +
            "    \"bom_id\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom/app")
    @RequiresPermissions(value =  {"enterprise:bm:master_bom:update","enterprise:bm:electrode_bom:update"},logical = Logical.OR)
    public Object bomToApp(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object>map = (Map)param.get("bom_info");
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            Bom bom=(Bom) BeanToMapUtil.convertMap(Bom.class,queryMap);
            bom.setCompanyId(param.get("company_id").toString());
            Bom appBom=new Bom();
            appBom.setBomId(bom.getBomId());
            appBom.setAppFlag("step30");
            appBom.setHandlerId(param.get("uid").toString());
            appBom.setHandledTime(new Date());
            int result = bomService.updateByPrimaryKeySelective(appBom);
            if(result>0) {
                modelMap.addAttribute("description", "BOM提交审批，成功！");
                return setModelMap(modelMap, HttpCode.OK, true);
            }
            else
            {
                modelMap.addAttribute("description", "BOM提交审批，失败！");
                return setModelMap(modelMap, HttpCode.OK, false);
            }

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "BOM提交审批,失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E010  BOM审批")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"bom_info\":{\n" +
            "    \"moldNo\":\"\",\n" +
            "    \"bomId\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/bom/app2")
    public Object bomApp(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Bom bom=(Bom) BeanToMapUtil.convertMap(Bom.class,(Map)param.get("bom_info"));
            bom.setCompanyId(param.get("company_id").toString());
            Map<String, Object> bomMap = (Map)param.get("bom_info");
            Boolean result = bomService.appBomRequire(bom.getBomId());
            if(result) {
                modelMap.addAttribute("description", "BOM审批，成功！");
                return setModelMap(modelMap, HttpCode.OK, true);
            }
            else
            {
                modelMap.addAttribute("description", "BOM审批，失败！");
                return setModelMap(modelMap, HttpCode.OK, false);
            }

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "BOM审批,失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
