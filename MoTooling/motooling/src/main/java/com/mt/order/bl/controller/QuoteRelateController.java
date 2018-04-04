package com.mt.order.bl.controller;

/**
 * created by fhk on 2017.10.24
 *
 * 报价单参考及关联
 *
 */

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.order.bl.service.QuoteService;
import com.mt.order.common.model.QuoteMold;
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
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "报价单参考及关联接口", description = "报价单参考及关联接口")
public class QuoteRelateController extends BaseController {

    @Autowired
    private QuoteService quoteService;

    @ApiOperation(value = "参考历史报价单")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_id_his\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/quote/referHis")
    @RequiresPermissions(value = "enterprise:sm:quote:create")
    public Object referHisQuote(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            ArrayList<Long> pQuoteIdHis = param.get("quote_id_his") == null || param.get("quote_id_his").toString().isEmpty() ? null : (ArrayList<Long>)param.get("quote_id_his");
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            /* Map quoteMap = quoteService.createQuoteNo(pQuoteIdHis);
            if (quoteMap == null || quoteMap.size() < 1) {
                modelMap.addAttribute("description", "生成新的报价单号失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Long quoteId = Long.parseLong(quoteMap.get("id").toString());
            String quoteNo = quoteMap.get("no").toString();

            List<QuoteMold> quoteMolds = quoteService.createReferHisQuote(quoteId, quoteNo, pQuoteIdHis);*/
            List<QuoteMold> quoteMolds = quoteService.createReferHisQuote(pQuoteId, pQuoteIdHis);

            /*Map<String, Object>retMap = new HashMap<>();
            retMap.put("quote_id", quoteId);
            retMap.put("quote_no", quoteNo);
            ArrayList<Map<String, Object>>lineList = new ArrayList<Map<String, Object>>();
            for(QuoteMold quoteMold: quoteMolds) {
                Map map = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMold));
                map.put("quote_line_id", map.get("id"));
                lineList.add(map);
            }
            retMap.put("quote_line_list", lineList);*/
            modelMap.addAttribute("description", "参考历史报价单，成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + ",失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "参考历史报价单，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "关联核算单生成报价单行信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"compute_id\":\"\",\n" +
            "\"quote_id\":\"\",\n" +
            "\"quote_line_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/quote/relateCompute")
    @RequiresPermissions(value = "enterprise:sm:quote:create")
    public Object relateComputeCreateQuoteLine(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            //Long[] pComputeIds = param.get("compute_ids") == null || param.get("compute_ids").toString().isEmpty() ? null : (Long[])param.get("compute_ids");
            ArrayList<Long> pComputeIds = param.get("compute_ids") == null || param.get("compute_ids").toString().isEmpty() ? null : (ArrayList<Long>)param.get("compute_ids");
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            //Long pQuoteLineId = param.get("quote_line_id") == null || param.get("quote_line_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_line_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            StringBuilder err = new StringBuilder();
            //Map<String, Object> mapQuoteMoldAndProds = new HashMap<>();
            boolean isOK = quoteService.createQuoteRelateCompute(pQuoteId, pComputeIds,null, err);
            if (!isOK) {
                modelMap.addAttribute("description",  err.toString());
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            /*Map<String, Object> retMap = new HashMap<>();
            Map<String, Object>dataMap = this.camelToUnderline(BeanToMapUtil.convertBean(mapQuoteMoldAndProds.get("quoteMold")));
            dataMap.put("quote_line_id", dataMap.get("id"));
            //dataMap.put("quote_line_id", ((QuoteMold)mapQuoteMoldAndProds.get("quoteMold")).getId());
            List<Map<String, Object>>prodsMap = this.camelToUnderline(BeanToMapUtil.convertBeanList((List<QuoteMoldProd>)mapQuoteMoldAndProds.get("quoteMoldProds")));
            dataMap.put("prod_list", prodsMap);
            retMap.put("quote_line_info", dataMap);*/
            modelMap.addAttribute("description", "关联核算单生成报价单行信息,成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + ",失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "关联核算单生成报价单行信息,失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

}
