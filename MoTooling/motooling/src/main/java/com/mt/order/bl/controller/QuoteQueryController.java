package com.mt.order.bl.controller;

/**
 * created by fhk on 2017.10.24
 *
 * 报价单信息查询
 *
 */

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.order.bl.service.DictService;
import com.mt.order.bl.service.QuoteService;
import com.mt.order.common.model.*;
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
@Api(value = "报价单查询接口", description = "报价单查询接口")
public class QuoteQueryController extends BaseController{

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private DictService dictService;


    @ApiOperation(value = "查询报价单付款信息列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/paylist/list")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryQuotePayList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer ver = param.get("version") == null || param.get("version").toString().isEmpty() ? null : Integer.parseInt(param.get("version").toString());

            List<QuotePaymentTerm> quotePaymentTermList = quoteService.selectPaymentsByQuoteId(pQuoteId, ver);
            Map<String, Object>retMap = new HashMap<>();
            retMap.put("pay_list", this.camelToUnderline(BeanToMapUtil.convertBeanList(quotePaymentTermList)));
            modelMap.addAttribute("description", "查询报价单付款信息列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询报价单付款信息列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    private Map<String, Object> getQuoteMoldDetail(Long pQuoteLineId, Integer version) {
        try {
            QuoteMold quoteMold = quoteService.selectQuoteMoldByQuoteLineId(pQuoteLineId, version);
            Map<String, Object> quoteMoldMap = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMold));
            quoteMoldMap.put("quote_line_id", quoteMoldMap.get("id"));
            quoteMoldMap.put("type", quoteMoldMap.get("type") == null ? null : quoteMoldMap.get("type").toString());
            dictService.getDictNameByCode(quoteMoldMap, "out_way", "out_way_name");
            dictService.getDictNameByCode(quoteMoldMap, "runner", "runner_name");
            dictService.getDictNameByCode(quoteMoldMap, "water_type", "water_type_name");

            List<QuoteMoldProd> quoteMoldProds = quoteService.selectQuoteMoldProdsByQuoteLineId(pQuoteLineId, version);
            ArrayList<Map<String, Object>> quoteMoldProdsMap = new ArrayList<Map<String, Object>>();
            for (QuoteMoldProd quoteMoldProd : quoteMoldProds) {
                Map<String, Object> map = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMoldProd));
                //Map<String, Object>picMap = new HashMap<>();
                //picMap.put("name", "模型（图纸）");
                //picMap.put("url", quoteMoldProd.getProdModelPic());
                map.put("prod_model_pic", quoteMoldProd.getProdModelPic());
                map.put("prod_id", quoteMoldProd.getId());
                quoteMoldProdsMap.add(map);
            }
            quoteMoldMap.put("prod_list", quoteMoldProdsMap);
            return quoteMoldMap;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }


    @ApiOperation(value = "查询报价单行信息列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote_line/list")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryQuoteLineInfoList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer ver = param.get("version") == null || param.get("version").toString().isEmpty() ? null : Integer.parseInt(param.get("version").toString());

            List<QuoteMold> quoteMoldList = quoteService.selectQuoteMoldsByQuoteId(pQuoteId, ver);
            ArrayList<Map<String, Object>> lineList = new ArrayList<Map<String, Object>>();
            for (QuoteMold quoteMold: quoteMoldList) {
                /*Map quoteMoldMap = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMold));
                quoteMoldMap.put("quote_line_id", quoteMoldMap.get("id"));
                lineList.add(quoteMoldMap);*/
                lineList.add(this.getQuoteMoldDetail(quoteMold.getId(), ver));
            }

            Map<String, Object>retMap = new HashMap<>();
            retMap.put("quote_line_list", lineList);
            modelMap.addAttribute("description", "查询报价单行信息列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询报价单行信息列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询报价单行信息明细")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_line_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote_line/getInfo")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryQuoteLineInfoDetail(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteLineId = param.get("quote_line_id") == null || param.get("quote_line_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_line_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer ver = param.get("version") == null || param.get("version").toString().isEmpty() ? null : Integer.parseInt(param.get("version").toString());

            /*QuoteMold quoteMold = quoteService.selectQuoteMoldByQuoteLineId(pQuoteLineId);
            Map<String, Object>quoteMoldMap = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMold));
            quoteMoldMap.put("quote_line_id", quoteMoldMap.get("id"));
            quoteMoldMap.put("type", quoteMoldMap.get("type") == null ? null : quoteMoldMap.get("type").toString());
            dictService.getDictNameByCode(quoteMoldMap, "out_way", "out_way_name");
            dictService.getDictNameByCode(quoteMoldMap, "runner", "runner_name");
            dictService.getDictNameByCode(quoteMoldMap, "water_type", "water_type_name");

            List<QuoteMoldProd> quoteMoldProds = quoteService.selectQuoteMoldProdsByQuoteLineId(pQuoteLineId);
            ArrayList<Map<String, Object>> quoteMoldProdsMap = new ArrayList<Map<String, Object>>();
            for (QuoteMoldProd quoteMoldProd: quoteMoldProds) {
                Map<String, Object>map = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMoldProd));
                //Map<String, Object>picMap = new HashMap<>();
                //picMap.put("name", "模型（图纸）");
                //picMap.put("url", quoteMoldProd.getProdModelPic());
                map.put("prod_model_pic", quoteMoldProd.getProdModelPic());
                map.put("prod_id", quoteMoldProd.getId());
                quoteMoldProdsMap.add(map);
            }
            quoteMoldMap.put("prod_list", quoteMoldProdsMap);*/
            Map<String, Object> quoteMoldMap = this.getQuoteMoldDetail(pQuoteLineId, ver);

            modelMap.addAttribute("description", "查询报价单行信息明细, 成功！");
            return setModelMap(modelMap, HttpCode.OK, quoteMoldMap);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + " 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询报价单行信息明细, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询报价单列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\t\n" +
            "\"curr_page\":\"\",\t\n" +
            "\"page_size\":\"\",\t\n" +
            "\"query\":{\n" +
            "    \"approve_step\":\"\",\n" +
            "    \"cst_company_id\":[1,2],\n" +
            "    \"end_date\":\"\",\n" +
            "    \"prod_name\":\"\",\n" +
            "    \"quote_no\":\"\",\n" +
            "    \"salesman\":\"\",\n" +
            "    \"start_date\":\"\",\n" +
            "    \"type\":[2] },\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote/list")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryQuoteList(@RequestBody Map<String, Object>param, ModelMap modelMap){
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pCurrPage = param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? null : Integer.parseInt(param.get("curr_page").toString());
            Integer pPageSize = param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? null : Integer.parseInt(param.get("page_size").toString());
            if (pCurrPage == null || pCurrPage < 1){ pCurrPage = 1;}
            if (pPageSize == null || pPageSize < 1) {pPageSize = 10;}
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("query");
            map.put("company_id", pCompanyId);
            map.put("curr_page", pCurrPage);
            map.put("page_size", pPageSize);
            map.put("page", (pCurrPage - 1)*pPageSize);
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            StringBuilder totalCount = new StringBuilder();
            List<Map<String, Object>> quotes = quoteService.selectQuotes(queryMap, totalCount);
            Map<String,Object> pageMap = this.createPageMap(Long.parseLong(totalCount.toString()), pPageSize, pCurrPage);
            pageMap.put("quote_list", quotes);
            modelMap.addAttribute("description", "查询报价单列表, 成功！");
            return setModelMap(modelMap, HttpCode.OK, pageMap);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询报价单列表, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询报价单头信息")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote/headDetail")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryQuoteHeadInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer ver = param.get("version") == null || param.get("version").toString().isEmpty() ? null : Integer.parseInt(param.get("version").toString());

            Quote quote = quoteService.selectQuoteByQuoteId(pQuoteId, ver);
            Map<String, Object> dataMap = this.camelToUnderline(BeanToMapUtil.convertBean(quote));
            dataMap.put("quote_id", dataMap.get("id"));
            dictService.getDictNameByCode(dataMap, "coin", "coin_name");
            modelMap.addAttribute("description", "查询报价单头信息，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询报价单头信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "查询报价单备注说明列表")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote/queryRemark")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryQuoteRemarkInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer ver = param.get("version") == null || param.get("version").toString().isEmpty() ? null : Integer.parseInt(param.get("version").toString());

            List<QuoteRemark> quoteRemarkList = quoteService.selectQuoteRemarksByQuoteId(pQuoteId, ver);

            Map<String, Object> retMap = new HashMap<>();
            retMap.put("remark_list", this.camelToUnderline(BeanToMapUtil.convertBeanList(quoteRemarkList)));
            modelMap.addAttribute("description", "查询报价单备注说明列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询报价单备注说明列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询某报价单的所有历史版本")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote/hisVersionList")
    @RequiresPermissions(value = "enterprise:sm:quote:view")
    public Object queryHisVersionList(@RequestBody Map<String, Object>param, ModelMap modelMap){
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            /*Map<String, Object>map = (Map)param.get("query");
            map.put("company_id", pCompanyId);
            map.put("curr_page", pCurrPage);
            map.put("page_size", pPageSize);
            map.put("page", (pCurrPage - 1)*pPageSize);
            Map<String, Object>queryMap = new HashMap<>();
            for (String key: map.keySet()) {
                queryMap.put(StringUtils.underlineToCamel(key), map.get(key));
            }
            StringBuilder totalCount = new StringBuilder();*/
            List<Map<String, Object>> quotes = quoteService.selectHisQuotes(pQuoteId);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("quote_list", quotes);
           /* Map<String,Object> pageMap = this.createPageMap(Long.parseLong(totalCount.toString()), pPageSize, pCurrPage);
            modelMap.put()
            pageMap.put("quote_list", quotes);*/
            modelMap.addAttribute("description", "查询某报价单的所有历史版本, 成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询某报价单的所有历史版本, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "报价单当前版本与上一版本比较")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote/versionCompare")
    @RequiresPermissions(value = "enterprurrVise:sm:quote:view")
    public Object quoteVersionCompare(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            //Integer ver = param.get("version") == null || param.get("version").toString().isEmpty() ? null : Integer.parseInt(param.get("version").toString());

            List<Map<String, Object>> retList = quoteService.getQuoteVersionCompare(pQuoteId);

            Map<String, Object>retMap = new HashMap<>();
            retMap.put("quote_line_list", retList);
            modelMap.addAttribute("description", "报价单版本比较，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "报价单版本比较，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
