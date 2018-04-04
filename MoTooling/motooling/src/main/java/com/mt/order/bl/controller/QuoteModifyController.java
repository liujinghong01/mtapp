package com.mt.order.bl.controller;

/**
 * created by fhk on 2017.10.24
 *
 * 报价单信息增删改
 *
 */

import com.mt.cms.common.model.OrigUserCompany;
import com.mt.cms.company.service.CompanyService;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.DictService;
import com.mt.order.bl.service.QuoteService;
import com.mt.order.common.model.Quote;
import com.mt.order.common.model.QuoteMold;
import com.mt.order.common.model.QuoteMoldProd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "报价单修改接口", description = "报价单修改接口")
public class QuoteModifyController extends BaseController {


    @Autowired
    private CompanyService companyService;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "报价单头信息新增修改 KH-B001")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"quote_head\":{\n" +
            "    \"avail_days\":\"\",\n" +
            "    \"coin\":\"\",\n" +
            "    \"connect_man\":\"\",\n" +
            "    \"cst_company_id\":\"\",\n" +
            "    \"cst_name\":\"\",\n" +
            "    \"delivery_place\":\"\",\n" +
            "    \"dep_id\":\"\",\n" +
            "    \"dep_name\":\"\",\n" +
            "    \"email\":\"\",\n" +
            "    \"handler_id\":\"\",\n" +
            "    \"phone\":\"\",\n" +
            "    \"prod_name\":\"\",\n" +
            "    \"quote_date\":\"\",\n" +
            "    \"quote_id\":\"\",\n" +
            "    \"quote_no\":\"\",\n" +
            "    \"salesman\":\"\"\n" +
            "    },\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote/modify")
    @RequiresPermissions(value = "enterprise:sm:quote:create,update")
    public Object modifyQuoteHeadInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try{
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            OrigUserCompany origUserCompany = companyService.selectUserCompanyByUid(pCompanyId, pUid);
            if (origUserCompany == null) {
                modelMap.addAttribute("description", "用户与公司不匹配，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object>pQuoteHead = param.get("quote_head") == null ? null : (Map)param.get("quote_head");

            Quote quote = new Quote();
            quote.setCompanyId(pCompanyId);
            quote.setAvailDays(pQuoteHead.get("avail_days") == null ? null : pQuoteHead.get("avail_days").toString());
            quote.setCoin(pQuoteHead.get("coin") == null ? null : pQuoteHead.get("coin").toString());
            quote.setConnectMan(pQuoteHead.get("connect_man") == null ? null : pQuoteHead.get("connect_man").toString());
            quote.setCstCompanyId(pQuoteHead.get("cst_company_id") == null || pQuoteHead.get("cst_company_id").toString().isEmpty() ? null : Integer.parseInt(pQuoteHead.get("cst_company_id").toString()));
            quote.setCstName(pQuoteHead.get("cst_name") == null ? null : pQuoteHead.get("cst_name").toString());
            quote.setEmail(pQuoteHead.get("email") == null ? null : pQuoteHead.get("email").toString());
            quote.setHandler(pQuoteHead.get("handler") == null || pQuoteHead.get("handler").toString().isEmpty() ? null : Long.parseLong(pQuoteHead.get("handler").toString()));
            quote.setHandlerName(pQuoteHead.get("handler_name") == null ? null : pQuoteHead.get("handler_name").toString());
            quote.setPhone(pQuoteHead.get("phone") == null ? null : pQuoteHead.get("phone").toString());
            quote.setProdName(pQuoteHead.get("prod_name") == null ? null : pQuoteHead.get("prod_name").toString());
            quote.setQuoteDate(pQuoteHead.get("quote_date") == null || pQuoteHead.get("quote_date").toString().isEmpty() ? null : BeanToMapUtil.convertStrToDate(pQuoteHead.get("quote_date").toString()));
            quote.setId(pQuoteHead.get("quote_id") == null || pQuoteHead.get("quote_id").toString().isEmpty() ? null : Long.parseLong(pQuoteHead.get("quote_id").toString()));
            quote.setQuoteNo(pQuoteHead.get("quote_no") == null ? null : pQuoteHead.get("quote_no").toString());
            quote.setSalesman(pQuoteHead.get("salesman") == null || pQuoteHead.get("salesman").toString().isEmpty() ? null : Long.parseLong(pQuoteHead.get("salesman").toString()));
            quote.setSalesmanName(pQuoteHead.get("salesman_name") == null ? null : pQuoteHead.get("salesman_name").toString());
            quote.setInvoiceType(pQuoteHead.get("invoice_type") == null ? null : Byte.valueOf(pQuoteHead.get("invoice_type").toString()));
            quote.setInvoiceTypeName(pQuoteHead.get("invoice_type_name") == null ? null : pQuoteHead.get("invoice_type_name").toString());
            quote.setTaxRatio(pQuoteHead.get("tax_ratio") == null || pQuoteHead.get("tax_ratio").toString().isEmpty() ? new BigDecimal(0) : new BigDecimal(pQuoteHead.get("tax_ratio").toString()) );
            quote.setDeliveryPlace(pQuoteHead.get("delivery_place") == null ? null : pQuoteHead.get("delivery_place").toString());

            quoteService.modifyQuoteHeadInfo(quote);
            quote = quoteService.selectQuoteByQuoteId(quote.getId());
            Map<String, Object> quouteMap = this.camelToUnderline(BeanToMapUtil.convertBean(quote));
            dictService.getDictNameByCode(quouteMap, "coin", "coin_name");
            quouteMap.put("quote_id", quote.getId());
            Map<String,Object>retMap = new HashMap<>();
            retMap.put("quote_head", quouteMap);
            modelMap.addAttribute("description", "报价单头信息新增修改，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "报价单头信息新增修改，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "报价单行信息的新增修改KH-B002")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_id\":\"\",\n" +
            "\"quote_line\":{\n" +
            "    \"count\":\"\",\n" +
            "    \"cst_mold_no\":\"\",\n" +
            "    \"height\":\"\",\n" +
            "    \"length\":\"\",\n" +
            "    \"material\":\"\",\n" +
            "    \"mold_mat\":\"\",\n" +
            "    \"out_way\":\"\",\n" +
            "    \"quote_line_id\":\"\",\n" +
            "    \"quote_price\":\"\",\n" +
            "    \"quote_up\":\"\",\n" +
            "    \"rebate\":\"\",\n" +
            "    \"rebate_price\":\"\",\n" +
            "    \"runner\":\"\",\n" +
            "    \"task_time\":\"\",\n" +
            "    \"type\":\"\",\n" +
            "    \"type_name\":\"\",\n" +
            "    \"water_type\":\"\",\n" +
            "    \"weight\":\"\",\n" +
            "    \"width\":\"\"\n" +
            "    },\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote_line/modify")
    @RequiresPermissions(value = "enterprise:sm:quote:create,update")
    public Object modifyQuoteLineInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            ///////////////////////////
            Map<String, Object>pQuoteLine = param.get("quote_line") == null ? null : (Map)param.get("quote_line");
            pQuoteLine.put("id", pQuoteLine.get("quote_line_id"));
            pQuoteLine.put("quote_id", pQuoteId);
            Map<String, Object>dataMap = new HashMap<>();
            for(String key: pQuoteLine.keySet()) {
                dataMap.put(StringUtils.underlineToCamel(key), pQuoteLine.get(key));
            }
            QuoteMold quoteMold = (QuoteMold)BeanToMapUtil.convertMap(QuoteMold.class, dataMap);
            List<QuoteMoldProd> quoteMoldProdList = new ArrayList<>();
            List<Map<String, Object>> prodMapList = this.underlineToCamel(pQuoteLine.get("prod_list") == null ? null : (List<Map<String, Object>>)pQuoteLine.get("prod_list"));
            if (prodMapList != null) {
                for (Map<String, Object> prodMap: prodMapList) {
                    QuoteMoldProd quoteMoldProd = (QuoteMoldProd)BeanToMapUtil.convertMap(QuoteMoldProd.class, prodMap);
                    quoteMoldProdList.add(quoteMoldProd);
                }
            }
            Long quoteLineId = quoteService.modifyQuoteLineInfo(quoteMold, quoteMoldProdList);
            quoteMold = quoteService.selectQuoteMoldByQuoteLineId(quoteLineId);
            prodMapList = this.camelToUnderline(BeanToMapUtil.convertBeanList(quoteService.selectQuoteMoldProdsByQuoteLineId(quoteMold.getId())));
            if (prodMapList != null) {
                for (Map<String, Object> prodMap: prodMapList) {
                    prodMap.put("prod_id", prodMap.get("id"));
                }
            }
            dataMap = this.camelToUnderline(BeanToMapUtil.convertBean(quoteMold));
            dataMap.put("prod_list", prodMapList);
            dataMap.put("quote_line_id", quoteMold.getId());
            dictService.getDictNameByCode(dataMap, "out_way", "out_way_name");
            dictService.getDictNameByCode(dataMap, "runner", "runner_name");
            dictService.getDictNameByCode(dataMap, "water_type", "water_type_name");
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("quote_id", pQuoteId);
            retMap.put("quote_line", dataMap);
            modelMap.addAttribute("description", "报价单行信息的新增修改，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "报价单行信息的新增修改，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "报价单制品信息的新增修改KH-B003")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"prod_Info\":[\n" +
            "\t{\"cavity_total\":\"\",\n" +
            "\t\"cst_prod_name\":\"\",\n" +
            "\t\"cst_prod_no\":\"\",\n" +
            "\t\"prod_id\":\"\",\n" +
            "\t\"prod_model_pic\":\"\" }\n" +
            "\t],\n" +
            "\"quote_line_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/quote/prodModify")
    @RequiresPermissions(value = "enterprise:sm:quote:create,update")
    public Object modifyQuoteProdInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteLineId = param.get("quote_line_id") == null || param.get("quote_line_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_line_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> dataMapList = quoteService.modifyQuoteMoldProdInfo(param);
            Map<String, Object>retMap = new HashMap<>();
            retMap.put("prod_info", dataMapList);
            modelMap.addAttribute("description", "报价单制品信息的新增修改，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "报价单制品信息的新增修改, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "报价单付款明细新增修改")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote_pay/modify")
    @RequiresPermissions(value = "enterprise:sm:quote:create,update")
    public Object modifyQuotePayInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> dataMapList = quoteService.modifyQuotePayment(param);
            Map<String, Object>retMap = new HashMap<>();
            retMap.put("pay_list", dataMapList);
            modelMap.addAttribute("description", "报价单付款明细新增修改，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "报价单付款明细新增修改，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "报价单备注新增修改")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote_remark/modify")
    @RequiresPermissions(value = "enterprise:sm:quote:create,update")
    public Object modifyQuoteRemarkInfo(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> dataMapList = quoteService.modifyQuoteRemark(param);
            Map<String, Object>retMap = new HashMap<>();
            retMap.put("remark_list", dataMapList);
            modelMap.addAttribute("description", "报价单备注新增修改，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "报价单备注新增修改，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "删除报价单")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote/delete")
    @RequiresPermissions(value = "enterprise:sm:quote:delete")
    public Object deleteQuote(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            quoteService.deleteQuote(pQuoteId);
            modelMap.addAttribute("description", "删除报价单, 成功！");
            return setModelMap(modelMap, HttpCode.OK);

        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "删除报价单, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);

        }
    }

    @ApiOperation(value = "删除报价单行信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"quote_line_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote_line/delete")
    @RequiresPermissions(value = "enterprise:sm:quote:delete")
    public Object deleteQuoteLine(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pQuoteLineId = param.get("quote_line_id") == null || param.get("quote_line_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_line_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            quoteService.deleteQuoteLine(pQuoteLineId);
            modelMap.addAttribute("description", "删除报价单行信息, 成功！");
            return setModelMap(modelMap, HttpCode.OK);

        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "删除报价单行信息, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);

        }
    }

    @ApiOperation(value = "删除报价单行信息的制品信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"prod_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote_line/prod/delete")
    @RequiresPermissions(value = "enterprise:sm:quote:delete")
    public Object deleteQuoteLineProd(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pProdId = param.get("prod_id") == null || param.get("prod_id").toString().isEmpty() ? null : Long.parseLong(param.get("prod_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            quoteService.deleteQuoteLineProd(pProdId);
            modelMap.addAttribute("description", "删除报价单行信息的制品信息, 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "删除报价单行信息的制品信息, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "删除报价单付款信息")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote_pay/delete")
    @RequiresPermissions(value = "enterprise:sm:quote:delete")
    public Object deleteQuotePay(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            ArrayList<Long> pPayIds = param.get("pay_id") == null ? null : (ArrayList<Long>)param.get("pay_id");
            if (pPayIds == null || pPayIds.isEmpty()) {
                modelMap.addAttribute("description", "付款信息无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            quoteService.deleteQuotePaymentByPayId(pPayIds);
            modelMap.addAttribute("description", "删除报价单付款信息, 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "删除报价单付款信息, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "删除报价单备注")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/quote_remark/delete")
    @RequiresPermissions(value = "enterprise:sm:quote:delete")
    public Object deleteQuoteRemark(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            ArrayList<Long> pRemarkIds = param.get("remark_id") == null ? null : (ArrayList<Long>)param.get("remark_id");
            if (pRemarkIds == null || pRemarkIds.isEmpty()) {
                modelMap.addAttribute("description", "备注信息无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            quoteService.deleteQuoteRemarkByRemarkId(pRemarkIds);
            modelMap.addAttribute("description", "删除报价单备注, 成功！");
            return setModelMap(modelMap, HttpCode.OK);

        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "删除报价单备注, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
