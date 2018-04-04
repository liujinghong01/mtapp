package com.mt.order.bl.service.impl;

/**
 * created by fhk on 2017.10.24
 * 报价单服务
 */

import com.mt.bm.bom.service.BomService;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.DictService;
import com.mt.order.bl.service.QuoteLogService;
import com.mt.order.bl.service.QuoteService;
import com.mt.order.common.dao.*;
import com.mt.order.common.model.*;
import com.mt.pc.common.dao.PcRequirePlanMapper;
import com.mt.pc.common.model.PcRequirePlan;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pc.purchase.service.CreateMatRequirePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteMapper quoteMapper;

    @Autowired
    private QuoteLogMapper quoteLogMapper;

    @Autowired
    private QuoteMoldMapper quoteMoldMapper;

    @Autowired
    private QuoteMoldLogMapper quoteMoldLogMapper;

    @Autowired
    private QuoteMoldProdMapper quoteMoldProdMapper;

    @Autowired
    private QuoteMoldProdLogMapper quoteMoldProdLogMapper;

    @Autowired
    private QuotePaymentTermMapper quotePaymentTermMapper;

    @Autowired
    private QuotePaymentTermLogMapper quotePaymentTermLogMapper;

    @Autowired
    private ComputeMapper computeMapper;

    @Autowired
    private ComputeCostMapper computeCostMapper;

    @Autowired
    private QuoteRemarkMapper quoteRemarkMapper;

    @Autowired
    private QuoteRemarkLogMapper quoteRemarkLogMapper;

    @Autowired
    private MoldMapper moldMapper;

    @Autowired
    private  BomService bomService;

    @Autowired
    private CreateMatRequirePlanService  createMatRequirePlanService;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private ProjectNotHwMapper projectNotHwMapper;

    @Autowired
    private QuoteLogService quoteLogService;

    @Autowired
    private DictService dictService;

    @Autowired
    private PcRequirePlanMapper pcRequirePlanMapper;

    /**
     * 是否新增业务数据
     * @param quote
     * @return
     */
    private boolean isAddQuote(Quote quote) {
        if (quote.getId() == null || quote.getId() < 1) { return true; }
        else { return false; }
    }
    private boolean isAddQuote(QuoteMold quoteMold) {
        if (quoteMold.getId() == null || quoteMold.getId() < 1) { return true; }
        else { return false; }
    }
    private boolean isAddQuote(QuoteMoldProd quoteMoldProd) {
        if (quoteMoldProd.getId() == null || quoteMoldProd.getId() < 1) { return true; }
        else { return false; }
    }
    private boolean isAddQuote(QuotePaymentTerm quotePaymentTerm) {
        if (quotePaymentTerm.getId() == null || quotePaymentTerm.getId() < 1) { return true; }
        else { return false; }
    }
    private boolean isAddQuote(QuoteRemark quoteRemark) {
        if (quoteRemark.getId() == null || quoteRemark.getId() < 1) { return true; }
        else { return false; }
    }

    /**
     * 根据历史报价号生成新报价单号及Id
     * @param hisQuoteId
     * @param uid
     * @return
     */
    @Override
    public Map<String, Object> createQuoteNo(Long hisQuoteId) {
        Integer companyId;
        Quote quote = quoteMapper.selectByPrimaryKey(hisQuoteId);
        companyId = quote.getCompanyId();
        return this.createQuoteNo(companyId);
    }

    /**
     * 生成报价单号
     * @return
     */
    @Override
    public Map<String, Object> createQuoteNo(Integer companyId) {
        //报价单号生成规则(15字符): ByyyyMMddnnnnnn
        //目前生成报价单号未与当前公司联系，形成个性化。未来有需要可考虑
        Map<String, Object>retMap = new HashMap<>();
        try {
            //调用通用单据生成接口
            Map<String , Object> map = createBillNoService.createBillNo(companyId, "QT");
            if ("1".equals(map.get("flag").toString())) {
               retMap.put("no", map.get("bill_no")) ;
               retMap.put("id", map.get("bill_id")) ;
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            retMap = null;
        }
        return  retMap;
    }

    /**
     * 非五金模定单下达时生成模号
     * @param orderLineId
     * @param moldNosCount
     * @return
     */
    @Override
    public ArrayList<String> createMoldNos(Long orderLineId, int moldNosCount) {
        ArrayList<String> retArr = new ArrayList<>();
        try {
            Integer companyId = projectNotHwMapper.getCompanyIdByProjectNotHwId(orderLineId);
            if (companyId == null || companyId < 1) { return null; }
            Map<String ,Object> map;
            for (int index = 0; index < moldNosCount; index ++){
                map = createBillNoService.createBillNo(companyId, "MO");
                if (map == null || map.get("flag") == null || !"1".equals(map.get("flag").toString())) { return null; }
                retArr.add(map.get("bill_no").toString());
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            retArr = null;
        }
        return  retArr;
    }

    /**
     * 生成报价单行Id
     * @return
     */
    @Override
    public Long createQuoteLineId() {
        Long maxId = quoteMoldMapper.getMaxQuoteLineId();
        if (maxId == null || maxId < 1) { return 1L; }
        else { return ++maxId; }
    }

    /**
     * 生成报价单制品Id
     * @return
     */
    @Override
    public Long createQuoteLineProdId(){
        Long maxId = quoteMoldProdMapper.getMaxProdId();
        if (maxId == null || maxId < 1) { return 1L; }
        else { return ++maxId; }
    }

    /**
     * 报价单头信息新增修改
     * @param quote
     * @return
     */
    @Override
    public int modifyQuoteHeadInfo(Quote quote) {
        if (this.isAddQuote(quote)) {
            Map<String, Object> retMap = this.createQuoteNo(quote.getCompanyId());
            quote.setId(Long.parseLong(retMap.get("id").toString()));
            quote.setQuoteNo(retMap.get("no").toString());
            quote.setVersion(1);
            return quoteMapper.insertSelective(quote);
        } else {
            StringBuilder app = new StringBuilder();
            Integer ver = quoteLogService.createNewQuoteLog(quote.getId(), app);
            quote.setVersion(ver);
            quote.setApproveStep(app.toString());
            return quoteMapper.updateByPrimaryKeySelective(quote);
        }
    }

    /**
     * 报价单行信息新增修改
     * @param quoteMold
     * @return
     */
    @Override
    public int modifyQuoteLineInfo(QuoteMold quoteMold){
        if (this.isAddQuote(quoteMold)) {
            Long lineId = this.createQuoteLineId();
            quoteMold.setId(lineId);
            return quoteMoldMapper.insertSelective(quoteMold);
        } else {
            return quoteMoldMapper.updateByPrimaryKeySelective(quoteMold);
        }
    }

    /**
     * 按取整方式返回数值
     * @param price
     * @param intMode  取整方式: 0无，1元，2十元，3百元，4千元，5万元
     * @return
     */
    private String getIntPrice(BigDecimal price, Byte intMode) {
        Double dPrice = price == null ? 0 : price.doubleValue();
        intMode = intMode == null ? 0 : intMode;
        Long val;

        switch (intMode) {
            case 0:
                val = Math.round(dPrice*100) / 100;
                return  val.toString();
            case 1:
                val =  Math.round(dPrice);
                // return price.setScale(0,BigDecimal.ROUND_HALF_UP).toBigInteger().toString();
                return val.toString();
            case 2:
                val =  Math.round(dPrice/10) * 10;
                return val.toString();
            case 3:
                val =  Math.round(dPrice/100) * 100;
                return val.toString();
            case 4:
                val =  Math.round(dPrice/1000) * 1000;
                return val.toString();
            case 5:
                val =  Math.round(dPrice/10000) * 10000;
                return val.toString();
            default:
                return  null;
        }
    }

    /**
     * 在行信息变化后，重新计算报价单头信息中的价格
     * @param quoteId
     * @return
     */
    @Override
    public int updateQuoteHeadPrice(Long quoteId) {
        return quoteMapper.updateQuoteHeadPrice(quoteId);
    }
    /**
     * 报价单行信息新增修改
     * @param quoteMold
     * @param quoteMoldProdList
     * @return
     */
    @Override
    public Long modifyQuoteLineInfo(QuoteMold quoteMold, List<QuoteMoldProd> quoteMoldProdList){
        Integer ver;
        quoteMold.setPriceTaxInt(this.getIntPrice(quoteMold.getPriceTax(), quoteMold.getIntMode()));
        quoteMold.setPriceLocalTaxInt(this.getIntPrice(quoteMold.getPriceLocalTax(), quoteMold.getIntMode()));
        quoteMold.setRebatePriceTaxInt(this.getIntPrice(quoteMold.getRebatePriceTax(), quoteMold.getIntMode()));

        if (this.isAddQuote(quoteMold)) {
            ver = quoteLogService.createNewQuoteLog(quoteMold.getQuoteId(), null);
            Long lineId = this.createQuoteLineId();
            quoteMold.setId(lineId);
            quoteMold.setVersion(ver);
            Quote quote = this.selectQuoteByQuoteId(quoteMold.getQuoteId());
            quoteMold.setQuoteNo(quote.getQuoteNo());
            quoteMoldMapper.insertSelective(quoteMold);
        } else {
            QuoteMold quoteMold1 = quoteMoldMapper.selectByPrimaryKey(quoteMold.getId());
            if ("1".equals(quoteMold1.getIsApproved()) && quoteMold1.getCount() > quoteMold.getCount()) {
                throw new IllegalArgumentException("已经审批通过的产品不能减少其数量");
            }
            ver = quoteLogService.createNewQuoteLog(quoteMold.getQuoteId(), null);
            quoteMold.setVersion(ver);
            quoteMoldMapper.updateByPrimaryKeySelective(quoteMold);
        }
        this.updateQuoteHeadPrice(quoteMold.getQuoteId());

        ////this.deleteProdsByLineId(quoteMold.getId());
        if (quoteMoldProdList != null) {
            for (QuoteMoldProd quoteMoldProd: quoteMoldProdList) {
                quoteMoldProd.setQuoteMoldId(quoteMold.getId());
                quoteMoldProd.setVersion(ver);
                this.modifyQuoteMoldProdInfo(quoteMoldProd);
            }
        }
        return quoteMold.getId();
    }

    /**
     * 报价单制品信息新增修改
     * @param quoteMoldProd
     * @return
     */
    @Override
    public int modifyQuoteMoldProdInfo(QuoteMoldProd quoteMoldProd) {
        if (this.isAddQuote(quoteMoldProd)) {
            Long maxId = quoteMoldProdMapper.getMaxProdId();
            if (maxId == null || maxId < 1) { maxId = 1L; }
            else { maxId ++; }
            quoteMoldProd.setId(maxId);
            return quoteMoldProdMapper.insertSelective(quoteMoldProd);
        } else {
            return quoteMoldProdMapper.updateByPrimaryKeySelective(quoteMoldProd);
        }
    }

    /**
     * 报价单制品信息新增修改
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> modifyQuoteMoldProdInfo(Map param) throws Exception{
        Long pQuoteLineId = param.get("quote_line_id") == null || param.get("quote_line_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_line_id").toString());
        QuoteMold quoteMold = quoteMoldMapper.selectByPrimaryKey(pQuoteLineId);
        Integer ver = quoteLogService.createNewQuoteLog(quoteMold.getQuoteId(), null);

        List<Map<String, Object>> dataMapList = new ArrayList<>();
        List<Map<String, Object>> pQuoteProdList = param.get("prod_Info") == null ? null : (List<Map<String, Object>>)param.get("prod_Info");
        if (pQuoteProdList == null) { return dataMapList; }

        for (Map<String, Object> pQuoteProd: pQuoteProdList) {
            if (pQuoteProd == null || pQuoteProd.isEmpty()) { continue; }
            if (pQuoteLineId == null || pQuoteLineId < 1) { continue; }
            pQuoteProd.put("id", pQuoteProd.get("prod_id"));
            pQuoteProd.put("quote_mold_id", pQuoteLineId);
            Map<String, Object> dataMap = new HashMap<>();
            for (String key : pQuoteProd.keySet()) {
                dataMap.put(StringUtils.underlineToCamel(key), pQuoteProd.get(key));
            }
            QuoteMoldProd quoteMoldProd = (QuoteMoldProd) BeanToMapUtil.convertMap(QuoteMoldProd.class, dataMap);
            quoteMoldProd.setVersion(ver);
            this.modifyQuoteMoldProdInfo(quoteMoldProd);

            Map prodMap = StringUtils.camelToUnderline(BeanToMapUtil.convertBean(this.selectQuoteMoldProdByProdId(quoteMoldProd.getId())));
            prodMap.put("prod_id", prodMap.get("id"));
            dataMapList.add(prodMap);
        }
        return dataMapList;
    }

    /**
     * 报价单付款信息新增修改
     * @param quotePaymentTerm
     * @return
     */
    @Override
    public  int modifyQuotePayment(QuotePaymentTerm quotePaymentTerm){
        if (this.isAddQuote(quotePaymentTerm)) {
            Long maxId = quotePaymentTermMapper.getMaxPayId();
            if (maxId == null || maxId < 1) { maxId = 1L; }
            else { maxId ++; }
            quotePaymentTerm.setId(maxId);
            return quotePaymentTermMapper.insertSelective(quotePaymentTerm);
        } else {
            return quotePaymentTermMapper.updateByPrimaryKeySelective(quotePaymentTerm);
        }
    }

    /**
     * 报价单付款信息新增修改
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> modifyQuotePayment(Map param) throws Exception {
        Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
        Integer ver = quoteLogService.createNewQuoteLog(pQuoteId, null);
        this.deleteQuotePaymentsByQuoteId(pQuoteId);
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        List<Map<String, Object>> pQuotePayList = param.get("pay_list") == null ? null : (List<Map<String, Object>>)param.get("pay_list");
        if (pQuotePayList == null) { return dataMapList; }
        for (Map<String, Object> pQuotePay: pQuotePayList) {
            if (pQuotePay == null || pQuotePay.isEmpty()) { continue; }
            pQuotePay.put("id", pQuotePay.get("pay_id"));
            pQuotePay.put("quote_id", pQuoteId);
            Map<String, Object> dataMap = new HashMap<>();
            for (String key : pQuotePay.keySet()) {
                dataMap.put(StringUtils.underlineToCamel(key), pQuotePay.get(key));
            }
            QuotePaymentTerm quotePaymentTerm = (QuotePaymentTerm) BeanToMapUtil.convertMap(QuotePaymentTerm.class, dataMap);
            quotePaymentTerm.setVersion(ver);
            this.modifyQuotePayment(quotePaymentTerm);
            Map payMap = StringUtils.camelToUnderline(BeanToMapUtil.convertBean(this.selectQuotePaymentByPayId(quotePaymentTerm.getId())));
            payMap.put("prod_id", payMap.get("id"));
            dataMapList.add(payMap);
        }
        return dataMapList;
    }

    /**
     * 报价单备注信息新增修改
     * @param quoteRemark
     * @return
     */
    @Override
    public int modifyQuoteRemark(QuoteRemark quoteRemark){
        if (this.isAddQuote(quoteRemark)) {
            Long maxId = quoteRemarkMapper.getMaxRemarkId();
            if (maxId == null || maxId < 1) { maxId = 1L; }
            else { maxId ++; }
            quoteRemark.setId(maxId);
            return quoteRemarkMapper.insertSelective(quoteRemark);
        } else {
            return quoteRemarkMapper.updateByPrimaryKeySelective(quoteRemark);
        }
    }

    /**
     * 报价单备注信息新增修改
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> modifyQuoteRemark(Map param) throws Exception{
        Long pQuoteId = param.get("quote_id") == null || param.get("quote_id").toString().isEmpty() ? null : Long.parseLong(param.get("quote_id").toString());
        this.deleteQuoteRemarksByQuoteId(pQuoteId);
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        List<Map<String, Object>> pQuoteRemarkList = param.get("remark_list") == null ? null : (List<Map<String, Object>>)param.get("remark_list");
        if(pQuoteRemarkList != null ) {
            for (Map<String, Object> pQuoteRemark : pQuoteRemarkList) {
                if (pQuoteRemark == null || pQuoteRemark.isEmpty()) { continue; }
                pQuoteRemark.put("id", pQuoteRemark.get("remark_id"));
                pQuoteRemark.put("quote_id", pQuoteId);
                Map<String, Object> dataMap = new HashMap<>();
                for (String key : pQuoteRemark.keySet()) {
                    dataMap.put(StringUtils.underlineToCamel(key), pQuoteRemark.get(key));
                }
                QuoteRemark quoteRemark = (QuoteRemark) BeanToMapUtil.convertMap(QuoteRemark.class, dataMap);
                this.modifyQuoteRemark(quoteRemark);
                Map remarkMap = StringUtils.camelToUnderline(BeanToMapUtil.convertBean(this.selectQuoteRemarkByRemarkId(quoteRemark.getId())));
                remarkMap.put("remark_id", remarkMap.get("id"));
                dataMapList.add(remarkMap);
            }
        }
        return dataMapList;
    }

    /**
     * 根据报价单Id获取付款信息
     * @param quoteId
     * @return
     */
    @Override
    public List<QuotePaymentTerm> selectPaymentsByQuoteId(Long quoteId) {
        return quotePaymentTermMapper.selectByQuoteId(quoteId);
    }

    @Override
    public List<QuotePaymentTerm>  selectPaymentsByQuoteId(Long quoteId, Integer version) {
        if (version == null || version < 1) {
            return this.selectPaymentsByQuoteId(quoteId);
        }
        return quotePaymentTermMapper.selectByQuoteIdAndVer(quoteId, version);
    }
    /**
     * 根据报价单Id获取行信息
     * @param quoteId
     * @return
     */
    @Override
    public List<QuoteMold> selectQuoteMoldsByQuoteId(Long quoteId) {
        return quoteMoldMapper.selectByQuoteId(quoteId);
    }
    @Override
    public List<QuoteMold> selectQuoteMoldsByQuoteId(Long quoteId, Integer version) {
        if (version == null || version < 1) {
            return quoteMoldMapper.selectByQuoteId(quoteId);
        }
        return quoteMoldMapper.selectByQuoteIdAndVer(quoteId, version);
    }

    /**
     * 根据报价单行Id获取行信息
     * @param id
     * @return
     */
    @Override
    public QuoteMold selectQuoteMoldByQuoteLineId(Long quoteLineId) {
        return quoteMoldMapper.selectByPrimaryKey(quoteLineId);
    }

    @Override
    public QuoteMold selectQuoteMoldByQuoteLineId(Long quoteLineId, Integer version) {
        if (version == null || version < 1) {
            return quoteMoldMapper.selectByPrimaryKey(quoteLineId);
        }
        return quoteMoldMapper.selectByPrimaryKeyAndVer(quoteLineId, version);
    }

    /**
     * 根据报价单行Id获取制品信息
     * @param quoteLineId
     * @return
     */
    @Override
    public List<QuoteMoldProd> selectQuoteMoldProdsByQuoteLineId(Long quoteLineId) {
        return quoteMoldProdMapper.selectByQuoteLineId(quoteLineId);
    }

    @Override
    public List<QuoteMoldProd> selectQuoteMoldProdsByQuoteLineId(Long quoteLineId, Integer version) {
        if (version == null || version < 1) {
            return quoteMoldProdMapper.selectByQuoteLineId(quoteLineId);
        }
        return quoteMoldProdMapper.selectByQuoteLineIdAndVer(quoteLineId, version);
    }

    /**
     * 根据报价单制品Id获取制品信息
     * @param prodId
     * @return
     */
    @Override
    public QuoteMoldProd selectQuoteMoldProdByProdId(Long prodId){
            return  quoteMoldProdMapper.selectByPrimaryKey(prodId);
    }

    /**
     * 获取报价单列表
     * @param queryMap
     * @param totalCount
     * @return
     */
    @Override
    public List<Map<String, Object>> selectQuotes(Map queryMap, StringBuilder totalCount) {
        //分页
        totalCount.append(quoteMapper.selectByConditionsCount(queryMap) + "");
        return quoteMapper.selectByConditions(queryMap);
    }

    @Override
    public List<Map<String, Object>> selectHisQuotes(Long quoteId) {
        return quoteMapper.selectHisQuotes(quoteId);
    }

    /**
     * 删除当前版本报价单，恢复上一个版本报价单（如果有）
     * @param quoteId
     * @return
     */
    @Override
    public int deleteQuote(Long quoteId) {
        /*List<QuoteMold> quoteMoldList = this.selectQuoteMoldsByQuoteId(quoteId);
        for (QuoteMold quoteMold: quoteMoldList) {
            this.deleteQuoteLine(quoteMold.getId());
        }
        this.deleteQuotePaymentsByQuoteId(quoteId);
        this.deleteQuoteRemarksByQuoteId(quoteId);

        Quote quote = new Quote();
        quote.setId(quoteId);
        quote.setStatus((byte)0);
        //只标记状态，不做物理删除
        return quoteMapper.updateByPrimaryKeySelective(quote);*/
        return quoteLogService.createRevertQuoteLog(quoteId);
    }

    /**
     * 删除报价单行信息
     * @param quoteLineId
     * @return
     */
    @Override
    public int deleteQuoteLine(Long quoteLineId) {
        QuoteMold quoteMold = quoteMoldMapper.selectByPrimaryKey(quoteLineId);
        if ("1".equals(quoteMold.getIsApproved())) {
            throw new IllegalArgumentException("该产品已经被审批通过，不能删除");
        }
        quoteLogService.createNewQuoteLog(quoteMold.getQuoteId(), null);

        quoteMoldProdMapper.deleteProdsByLineId(quoteLineId);
        quoteMold = new QuoteMold();
        quoteMold.setId(quoteLineId);
        quoteMold.setStatus(Byte.parseByte(0 + ""));
        ////return quoteMoldMapper.deleteByPrimaryKey(quoteLineId);
        return quoteMoldMapper.updateByPrimaryKeySelective(quoteMold);
    }

    /**
     * 删除报价单制品信息
     * @param prodId
     * @return
     */
    @Override
    public int deleteQuoteLineProd(Long prodId) {
        QuoteMoldProd quoteMoldProd = quoteMoldProdMapper.selectByPrimaryKey(prodId);
        QuoteMold quoteMold = quoteMoldMapper.selectByPrimaryKey(quoteMoldProd.getQuoteMoldId());
        quoteLogService.createNewQuoteLog(quoteMold.getQuoteId(), null);

        ////return quoteMoldProdMapper.deleteByPrimaryKey(prodId);
        quoteMoldProd = new QuoteMoldProd();
        quoteMoldProd.setId(prodId);
        quoteMoldProd.setStatus(Byte.parseByte(0 + ""));
        return quoteMoldProdMapper.updateByPrimaryKeySelective(quoteMoldProd);
    }

    /**
     * 根据报价单号获取报价单
     * @param quoteId
     * @return
     */
    @Override
    public Quote selectQuoteByQuoteId(Long quoteId) {
        return quoteMapper.selectByPrimaryKey(quoteId);
    }
    @Override
    public Quote selectQuoteByQuoteId(Long quoteId, Integer version) {
        if (version == null || version < 1) {
            return quoteMapper.selectByPrimaryKey(quoteId);
        }
        return quoteMapper.selectByPrimaryKeyAndVer(quoteId, version);
    }
    @Override
    public int insertQuote(Quote quote) {
        return quoteMapper.insertSelective(quote);
    }

    @Override
    public int insertQuoteLine(QuoteMold quoteMold) {
        return quoteMoldMapper.insertSelective(quoteMold);
    }

    @Override
    public int insertQuoteLineProd(QuoteMoldProd quoteMoldProd){
        return quoteMoldProdMapper.insertSelective(quoteMoldProd);
    }

    @Override
    public int deleteProdsByLineId(Long quoteLineId) {
        return quoteMoldProdMapper.deleteProdsByLineId(quoteLineId);
    }

    @Override
    public int deleteQuotePaymentsByQuoteId(Long quoteId) {
        return quotePaymentTermMapper.deleteQuotePaymentsByQuoteId(quoteId);
    }

    @Override
    public int deleteQuoteRemarksByQuoteId(Long quoteId){
        return quoteRemarkMapper.deleteQuoteRemarksByQuoteId(quoteId);
    }

    /**
     * 获取被某核算单关联过的报价单行信息
     * @param quoteId
     * @param computeId
     * @return
     */
    @Override
    public List<QuoteMold> selectQuoteMoldsByComputeId(Long quoteId, Long computeId){
        Map<String, Object>map = new HashMap<>();
        map.put("quoteId", quoteId);
        map.put("computeId", computeId);
        return quoteMoldMapper.selectQuoteMoldsByComputeId(map);
    }

    /*@Override
    public  boolean createQuoteRelateCompute(Long quoteId, Long quoteLineId, Long computeId, Map mapQuoteMoldAndProds, StringBuilder err){
        List<QuoteMold> quoteMolds = this.selectQuoteMoldsByComputeId(quoteId, computeId);
        if (quoteMolds != null && quoteMolds.size() > 0) {
            err.append("该核算单已经被当前报价单关联了，不能多次关联,失败！");
            return false;
        }
        Compute compute = computeMapper.selectByPrimaryKey(computeId);

        QuoteMold quoteMold = this.selectQuoteMoldByQuoteLineId(quoteLineId);
        if (quoteMold == null) {
            quoteMold = new QuoteMold();
            quoteMold.setQuoteId(quoteId);
            //quoteLineId = createQuoteLineId();
            //quoteMold.setId(quoteLineId);
        }
        ////quoteMoldProds = this.selectQuoteMoldProdsByQuoteLineId(quoteLineId);
        quoteMold.setComputeId(computeId);
        quoteMold.setCstMoldNo(compute.getCstMoldNo());
        quoteMold.setMaterial(compute.getMaterial());
        quoteMold.setType(compute.getType());
        quoteMold.setTypeName(compute.getTypeName());
        this.modifyQuoteLineInfo(quoteMold);

        quoteMoldProdMapper.deleteProdsByLineId(quoteMold.getId());
        mapQuoteMoldAndProds.put("quoteMold", quoteMold);
        return true;
    }*/

    /**
     * 报价单关联核算单
     * @param quoteId
     * @param quoteLineId
     * @param computeId
     * @param err
     * @return
     */
    @Override
    public  boolean createQuoteRelateCompute(Long quoteId, Long quoteLineId, Long computeId, Map mapQuoteMoldAndProds, StringBuilder err){
        /*List<QuoteMold> quoteMolds = this.selectQuoteMoldsByComputeId(quoteId, computeId);
        if (quoteMolds != null && quoteMolds.size() > 0) {
            err.append("该核算单已经被当前报价单关联了，不能多次关联,失败！");
            return false;
        }*/
        Compute compute = computeMapper.selectByPrimaryKey(computeId);
        ComputeCost computeCost = computeCostMapper.selectCostDetail(computeId.intValue());

        Integer ver = quoteLogService.createNewQuoteLog(quoteId, null);
        Quote quote = this.selectQuoteByQuoteId(quoteId);
        QuoteMold quoteMold = this.selectQuoteMoldByQuoteLineId(quoteLineId);
        if (quoteMold == null) {
            quoteMold = new QuoteMold();
            quoteMold.setQuoteId(quoteId);
            //quoteLineId = createQuoteLineId();
            //quoteMold.setId(quoteLineId);
        }
        ////quoteMoldProds = this.selectQuoteMoldProdsByQuoteLineId(quoteLineId);
        quoteMold.setVersion(ver);
        quoteMold.setComputeId(computeId);
        quoteMold.setComputeNo(compute.getComputeNo());
        quoteMold.setQuoteNo(quote.getQuoteNo());
        quoteMold.setCstMoldNo(compute.getCstMoldNo());
        quoteMold.setMaterial(compute.getMaterial());
        quoteMold.setType(compute.getType());
        quoteMold.setTypeName(compute.getTypeName());
        quoteMold.setWeight(computeCost.getFixingTon());
        quoteMold.setUp(computeCost.getMoldUp());
        quoteMold.setUpLocal(computeCost.getMoldUpLocal());
        //目前暂定产品数量为1个,折扣暂定为1
        quoteMold.setCount(1);
        quoteMold.setRebate(new BigDecimal(1));
        quoteMold.setIntMode(Byte.valueOf("0"));
        this.setQuotePrice(quoteMold);
        this.modifyQuoteLineInfo(quoteMold);

        quoteLineId = quoteMold.getId();
        quoteMoldProdMapper.deleteProdsByLineId(quoteLineId);
        QuoteMoldProd quoteMoldProd = new QuoteMoldProd();
        quoteMoldProd.setVersion(ver);
        quoteMoldProd.setCstProdName(compute.getProdInfo());
        quoteMoldProd.setCstProdNo("1");
        quoteMoldProd.setCavityTotal(1);
        quoteMoldProd.setQuoteMoldId(quoteLineId);
        this.modifyQuoteMoldProdInfo(quoteMoldProd);

        //mapQuoteMoldAndProds.put("quoteMold", quoteMold);
        return true;
    }

    @Override
    public boolean createQuoteRelateCompute(Long quoteId, ArrayList<Long> computeIds, Map mapQuoteMoldAndProds, StringBuilder err) {
        for(Object computeId: computeIds) {
            if (computeId == null) { continue; }
            this.createQuoteRelateCompute(quoteId, null, Long.valueOf(computeId.toString()), mapQuoteMoldAndProds, err);
        }
        return true;
    }

    @Override
    public QuotePaymentTerm selectQuotePaymentByPayId(Long payId) {
        return quotePaymentTermMapper.selectByPrimaryKey(payId);
    }

    @Override
    public QuoteRemark selectQuoteRemarkByRemarkId(Long remarkId){
        return quoteRemarkMapper.selectByPrimaryKey(remarkId);
    }

    @Override
    public List<QuoteRemark> selectQuoteRemarksByQuoteId(Long quoteId){
        return quoteRemarkMapper.selectQuoteRemarksByQuoteId(quoteId);
    }
    @Override
    public List<QuoteRemark> selectQuoteRemarksByQuoteId(Long quoteId, Integer version) {
        if (version == null || version < 1) {
            return  this.selectQuoteRemarksByQuoteId(quoteId);
        }
        return  quoteRemarkMapper.selectQuoteRemarksByQuoteIdAndVer(quoteId, version);
    }
    /**
     * 根据付款Id删除报价单付款记录
     * @param payId
     * @return
     */
    @Override
    public int deleteQuotePaymentByPayId(Long payId){
        QuotePaymentTerm quotePaymentTerm = new QuotePaymentTerm();
        quotePaymentTerm.setId(payId);
        quotePaymentTerm.setStatus((byte)0);
        return quotePaymentTermMapper.updateByPrimaryKeySelective(quotePaymentTerm);
    }

    /**
     * 根据付款Id删除报价单付款记录
     * @param payIds
     */
    @Override
    public void deleteQuotePaymentByPayId(ArrayList<Long> payIds){
        for (Long payId: payIds) {
            QuotePaymentTerm quotePaymentTerm = quotePaymentTermMapper.selectByPrimaryKey(payId);
            quoteLogService.createNewQuoteLog(quotePaymentTerm.getQuoteId(), null);

            this.deleteQuotePaymentByPayId(payId);
        }
    }

    /**
     * 根据备注Id删除报价单备注记录
     * @param remarkId
     * @return
     */
    @Override
    public int deleteQuoteRemarkByRemarkId(Long remarkId) {
        QuoteRemark quoteRemark = new QuoteRemark();
        quoteRemark.setId(remarkId);
        quoteRemark.setStatus((byte)0);
        return quoteRemarkMapper.updateByPrimaryKeySelective(quoteRemark);
    }

    /**
     * 根据备注Id删除报价单备注记录
     * @param remarkIds
     */
    @Override
    public void deleteQuoteRemarkByRemarkId(ArrayList<Long> remarkIds){
        for (Long remarkId: remarkIds) {
            QuoteRemark quoteRemark = quoteRemarkMapper.selectByPrimaryKey(remarkId);
            quoteLogService.createNewQuoteLog(quoteRemark.getQuoteId(), null);

            this.deleteQuoteRemarkByRemarkId(remarkId);
        }
    }

    /**
     * 根据定单行Id获取该行产品生成的模号信息
     * @param orderLineId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMoldNosByOrderLineId(Long orderLineId) {
        return moldMapper.getMoldNosByOrderLineId(orderLineId);
    }

    @Override
    public Boolean OrderOutPurchRequireIsDealed(Long projectNotHwId) {
        Map<String, Object> map = new HashMap<>();
        map.put("create_type", 2);
        map.put("project_not_hw_id", projectNotHwId);
        List<PcRequirePlan> pcRequirePlanList = pcRequirePlanMapper.selectByNotHwIdAndCreateType(map);
        if (pcRequirePlanList == null) { return false; }
        for(PcRequirePlan pcRequirePlan: pcRequirePlanList) {
            if (pcRequirePlan.getBuyQty() == null || pcRequirePlan.getBuyQty().compareTo(new BigDecimal(0)) == 0) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public int deleteOrderOutPurchRequire(Long projectNotHwId) {
        Map<String, Object> map = new HashMap<>();
        map.put("create_type", 2);
        map.put("project_not_hw_id", projectNotHwId);
        List<PcRequirePlan> pcRequirePlanList = pcRequirePlanMapper.selectByNotHwIdAndCreateType(map);
        if (pcRequirePlanList == null) { return 0; }
        for(PcRequirePlan pcRequirePlan: pcRequirePlanList) {
            if (pcRequirePlan.getBuyQty() == null || pcRequirePlan.getBuyQty().compareTo(new BigDecimal(0)) == 0) {
                pcRequirePlanMapper.deleteByPrimaryKey(pcRequirePlan.getId());
            } else {
                throw new IllegalArgumentException("需求已经处理");
            }
        }
        return 0;
    }
    /**
     * 定单下达：设置待下达的定单行产品为下达
     * @param orderStartupMap
     * @return
     */
    @Override
    public List<Map<String, Object>> createOrderStartup(Map<String, Object> orderStartupMap) {
        Long orderLineId = Long.parseLong(orderStartupMap.get("order_line_id").toString());
        Map<String, Object> projectNotHwMap = moldMapper.getStartupStepByOrderLineId(orderLineId);
        if (projectNotHwMap == null || projectNotHwMap.get("startup_step") == null) {
            throw new IllegalArgumentException("无效的定单状态");
        }
        // 定单管理已下达数据中应能对下达信息进行更改，其中自制能改为外购（无限制条件），如果自制时有生成Bom，改外购时仍然要生成Bom；
        // 并外购需求一旦被处理后将不能改为自制。外购改成自制时，将删除成品的采购需求；
        // orderStartupMap.get("sup_type"):1自制，2外购
        if (!"step100".equals(projectNotHwMap.get("startup_step").toString().trim())) {
            if (!"step200".equals(projectNotHwMap.get("startup_step").toString())) {
                throw new IllegalArgumentException("暂停或完结产品不能再次下达");
            } else if (orderStartupMap.get("sup_type").equals(projectNotHwMap.get("sup_type"))) {
                throw new IllegalArgumentException("该产品已经下达");
            } else if ("2".equals(orderStartupMap.get("sup_type"))) {
                // 从自制改为外购
                if (!"1".equals(orderStartupMap.get("is_crt_bom"))) {
                    throw new IllegalArgumentException("自制更改为外购，要生成Bom");
                }
            } else {
                // 从外购改为自制
                if (this.OrderOutPurchRequireIsDealed(orderLineId)) {
                    throw new IllegalArgumentException("之前的外购需求已经处理,不能再更改为自制");
                } else {
                    //删除之前的外购需求
                    this.deleteOrderOutPurchRequire(orderLineId);
                }
            }
        }
        orderStartupMap.put("orderLineId", orderLineId);
        orderStartupMap.put("startup_step", "step200");
        moldMapper.orderStartupLineInfo(orderStartupMap);
        Integer count = Integer.parseInt(orderStartupMap.get("count").toString());
        boolean isBatchProcess = orderStartupMap.get("is_batch_process") == null ? false : "1".equals(orderStartupMap.get("is_batch_process").toString().trim());
        Integer moldNosCount;
        if (count == 1) { moldNosCount = 1; }
        else if (isBatchProcess) { moldNosCount = 1; }
        else { moldNosCount = count; }
        if (moldNosCount < 1) { return null; }

        ////Map<String, Object> createBomMap = new HashMap<>();
        ////createBomMap.put("uid", orderStartupMap.get("uid"));
        Byte moldType;
        ArrayList<String> moldNos = this.createMoldNos(orderLineId, moldNosCount);
        for (String moldNo: moldNos) {
            if (projectNotHwMap.get("type") == null || projectNotHwMap.get("type").toString().trim().isEmpty()) {
                moldType = 1;
            } else {
                moldType = Byte.parseByte(projectNotHwMap.get("type").toString());
                if (moldType != 1 && moldType != 2) { moldType = 1; }
            }
            //目前只做: 1注塑模, 2压铸模
            orderStartupMap.put("mold_type", moldType);
            orderStartupMap.put("project_not_hw_id", orderLineId);
            orderStartupMap.put("mold_no", moldNo);
            orderStartupMap.put("mold_price", orderStartupMap.get("up_not_tax"));
            orderStartupMap.put("startup_step", "step200");
            orderStartupMap.put("batch_count", isBatchProcess ? count : 1);
            orderStartupMap.put("company_id", projectNotHwMap.get("company_id"));
            orderStartupMap.put("company_name", projectNotHwMap.get("company_name"));
            orderStartupMap.put("cst_prod_name", projectNotHwMap.get("cst_prod_name"));
            orderStartupMap.put("cst_company_id", projectNotHwMap.get("cst_company_id"));
            orderStartupMap.put("cst_name", projectNotHwMap.get("cst_name"));
            orderStartupMap.put("project_id", projectNotHwMap.get("project_id"));
            orderStartupMap.put("project_no", projectNotHwMap.get("project_no"));

            moldMapper.orderStartupMold(orderStartupMap);
            ////createBomMap.put("mold_no", moldNo);
            ////moldMapper.createBom(createBomMap);
            bomService.createBom(orderStartupMap.get("is_crt_bom").toString(), moldNo, orderStartupMap.get("uid").toString(), projectNotHwMap.get("company_id").toString(),
                    isBatchProcess ? count : 1, (Date)projectNotHwMap.get("first_try_date"));
            //生成物料及如果是采购还要生成物料需求计划
            createMatRequirePlanService.createMatRequirePlanfromMold(Integer.parseInt(projectNotHwMap.get("company_id").toString()), moldNo, Long.parseLong(orderStartupMap.get("uid").toString()));
        }
        return getMoldNosByOrderLineId(Long.parseLong(orderStartupMap.get("order_line_id").toString()));
    }

    /*@Override
    public List<QuoteMold> createReferHisQuote(Long newQuoteId, String newQuoteNo, Long quoteIdHis){
        Long lindId = this.createQuoteLineId();
        Long prodId = this.createQuoteLineProdId();

        Quote quote = this.selectQuoteByQuoteId(quoteIdHis);
        quote.setId(newQuoteId);
        quote.setQuoteNo(newQuoteNo);
        List<QuoteMold> quoteMolds = this.selectQuoteMoldsByQuoteId(quoteIdHis);
        ArrayList<Object>arrQuoteMoldProds = new ArrayList<Object>();
        for (QuoteMold quoteMold: quoteMolds) {
            List<QuoteMoldProd> quoteMoldProds = this.selectQuoteMoldProdsByQuoteLineId(quoteMold.getId());
            for (QuoteMoldProd quoteMoldProd: quoteMoldProds) {
                quoteMoldProd.setId(prodId ++);
                quoteMoldProd.setQuoteMoldId(lindId);
            }
            arrQuoteMoldProds.add(quoteMoldProds);

            quoteMold.setId(lindId);
            quoteMold.setQuoteId(newQuoteId);
            quoteMold.setQuoteNo(newQuoteNo);
            lindId ++;
        }
        //得到当前时间
        quote.setQuoteDate(null);
        //初始化审批状态
        quote.setApproveStep("step10");
        quote.setApproveSugg("");
        this.insertQuote(quote);
        for (QuoteMold quoteMold: quoteMolds) {
            this.insertQuoteLine(quoteMold);
        }
        for (Object o1: arrQuoteMoldProds) {
            for (QuoteMoldProd quoteMoldProd: (List<QuoteMoldProd>)o1) {
                this.insertQuoteLineProd(quoteMoldProd);
            }
        }
        return quoteMolds;
    }*/

    /**
     * 参考历史报价单生成新的报价单
     * @param newQuoteId
     * @param newQuoteNo
     * @param quoteIdHis
     * @return
     */
    @Override
    public List<QuoteMold> createReferHisQuote(Long newQuoteId, String newQuoteNo, Long quoteIdHis){
        Long lindId = this.createQuoteLineId();
        Long prodId = this.createQuoteLineProdId();

        Quote quote = this.selectQuoteByQuoteId(quoteIdHis);
        quote.setId(newQuoteId);
        quote.setQuoteNo(newQuoteNo);
        List<QuoteMold> quoteMolds = this.selectQuoteMoldsByQuoteId(quoteIdHis);
        ArrayList<Object>arrQuoteMoldProds = new ArrayList<Object>();
        for (QuoteMold quoteMold: quoteMolds) {
            List<QuoteMoldProd> quoteMoldProds = this.selectQuoteMoldProdsByQuoteLineId(quoteMold.getId());
            for (QuoteMoldProd quoteMoldProd: quoteMoldProds) {
                quoteMoldProd.setId(prodId ++);
                quoteMoldProd.setQuoteMoldId(lindId);
            }
            arrQuoteMoldProds.add(quoteMoldProds);

            quoteMold.setId(lindId);
            quoteMold.setQuoteId(newQuoteId);
            quoteMold.setQuoteNo(newQuoteNo);
            lindId ++;
        }
        //得到当前时间
        quote.setQuoteDate(null);
        //初始化审批状态
        quote.setApproveStep("step10");
        quote.setApproveSugg("");
        this.insertQuote(quote);
        for (QuoteMold quoteMold: quoteMolds) {
            this.insertQuoteLine(quoteMold);
        }
        for (Object o1: arrQuoteMoldProds) {
            for (QuoteMoldProd quoteMoldProd: (List<QuoteMoldProd>)o1) {
                this.insertQuoteLineProd(quoteMoldProd);
            }
        }
        return quoteMolds;
    }

    private void setQuotePrice(QuoteMold quoteMold) {
        Quote quote = this.selectQuoteByQuoteId(quoteMold.getQuoteId());

        BigDecimal count = new BigDecimal(quoteMold.getCount());
        if (count == null) return;

        quoteMold.setPrice(quoteMold.getUp() == null ? null : quoteMold.getUp().multiply(count));
        quoteMold.setPriceLocal(quoteMold.getUpLocal() == null ? null : quoteMold.getUpLocal().multiply(count));
        quoteMold.setRebatePrice(quoteMold.getPrice() == null ? null : quoteMold.getPrice().multiply(quoteMold.getRebate() == null ?  new BigDecimal(1) : quoteMold.getRebate()));

        quoteMold.setUpTax(quoteMold.getUp() == null ? null : quoteMold.getUp().add(quoteMold.getUp() == null ? null : quoteMold.getUp().multiply(quote.getTaxRatio() == null ? new BigDecimal(1) : quote.getTaxRatio())));
        quoteMold.setUpLocalTax(quoteMold.getUpLocal() == null ? null : quoteMold.getUpLocal().add(quoteMold.getUpLocal() == null ? null : quoteMold.getUpLocal().multiply(quote.getTaxRatio() == null ? new BigDecimal(1) : quote.getTaxRatio())));

        quoteMold.setPriceTax(quoteMold.getUpTax() == null ? null : quoteMold.getUpTax().multiply(count));
        quoteMold.setPriceLocalTax(quoteMold.getUpLocalTax() == null ? null : quoteMold.getUpLocalTax().multiply(count));
        quoteMold.setRebatePriceTax(quoteMold.getPriceTax() == null ? null : quoteMold.getPriceTax().multiply(quoteMold.getRebate() == null ? new BigDecimal(1) : quoteMold.getRebate()));

        quoteMold.setPriceTaxInt(this.getIntPrice(quoteMold.getPriceTax(), quoteMold.getIntMode()));
        quoteMold.setPriceLocalTaxInt(this.getIntPrice(quoteMold.getPriceLocalTax(), quoteMold.getIntMode()));
        quoteMold.setRebatePriceTaxInt(this.getIntPrice(quoteMold.getRebatePriceTax(), quoteMold.getIntMode()));
    }
    /**
     * 一个已存在的报价单可以参考多个历史报价单，但只取这些历史报价单的行信息生成新的行信息；
     * @param quoteId
     * @param hisQuoteIds
     * @return
     */
    @Override
    public List<QuoteMold> createReferHisQuote(Long quoteId, ArrayList<Long> hisQuoteIds) {
        Quote quote = this.selectQuoteByQuoteId(quoteId);
        if (quote == null) {
            throw new IllegalArgumentException("报价单不存在");
        }
        List<QuoteMold> quoteMolds = null;
        for (Object object1: hisQuoteIds) {
            if (object1 == null) continue;
            Long quoteIdHis = Long.valueOf(object1.toString());
            Integer ver = quoteLogService.createNewQuoteLog(quoteId, null);

            Long lindId = this.createQuoteLineId();
            Long prodId = this.createQuoteLineProdId();

            /*Quote quote = this.selectQuoteByQuoteId(quoteIdHis);
            quote.setId(newQuoteId);
            quote.setQuoteNo(newQuoteNo);*/
            quoteMolds = this.selectQuoteMoldsByQuoteId(quoteIdHis);
            ArrayList<Object>arrQuoteMoldProds = new ArrayList<Object>();
            for (QuoteMold quoteMold: quoteMolds) {
                List<QuoteMoldProd> quoteMoldProds = this.selectQuoteMoldProdsByQuoteLineId(quoteMold.getId());
                for (QuoteMoldProd quoteMoldProd: quoteMoldProds) {
                    quoteMoldProd.setId(prodId ++);
                    quoteMoldProd.setQuoteMoldId(lindId);
                    quoteMoldProd.setVersion(ver);
                }
                arrQuoteMoldProds.add(quoteMoldProds);

                quoteMold.setId(lindId);
                quoteMold.setQuoteId(quoteId);
                quoteMold.setQuoteNo(quote.getQuoteNo());
                quoteMold.setVersion(ver);
                this.setQuotePrice(quoteMold);

                lindId ++;
            }
            /*//得到当前时间
            quote.setQuoteDate(null);
            //初始化审批状态
            quote.setApproveStep("step10");
            quote.setApproveSugg("");
            this.insertQuote(quote);*/
            for (QuoteMold quoteMold: quoteMolds) {
                this.insertQuoteLine(quoteMold);
            }
            for (Object o1: arrQuoteMoldProds) {
                for (QuoteMoldProd quoteMoldProd: (List<QuoteMoldProd>)o1) {
                    this.insertQuoteLineProd(quoteMoldProd);
                }
            }
        }
        return quoteMolds;
    }

    private boolean isNotModified(Object o1, Object o2) {
        switch (o1.getClass().getName()) {
            case "java.lang.Integer":
                return (o1 == null ? (Integer)0 : (Integer)o1).equals (o2 == null ? (Integer)0 : (Integer)o2);
            case "java.lang.Long":
                return (o1 == null ? (Long)0L : (Long)o1).equals(o2 == null ? (Long)0L : (Long)o2);
            case "java.lang.Byte":
                return (o1 == null ? Byte.valueOf("0") : (Byte)o1).equals(o2 == null ? Byte.valueOf("0") : (Byte)o2);
            case "java.math.BigDecimal":
                return (o1 == null ? 0 : (BigDecimal)o1).equals(o2 == null ? 0 : (BigDecimal)o2);
            case "java.math.BigInteger":
                return (o1 == null ? 0 : (BigInteger)o1).equals(o2 == null ? 0 : (BigInteger)o2);
            case "java.lang.String":
                return (o1 == null ? "" : (String)o1).equals(o2 == null ? "" : (String)o2);
            case "java.lang.Boolean":
                return !((o1 == null ? false : (Boolean) o1) ^ (o2 == null ? false : (Boolean)o2));
            case "java.util.Date":
                String date1 = o1 == null ? "1900-01-01" : DateUtil.dateToString((Date)o1, DateUtil.DATE_PATTERN.YYYY_MM_DD);
                String date2 = o2 == null ? "1900-01-01" : DateUtil.dateToString((Date)o2, DateUtil.DATE_PATTERN.YYYY_MM_DD);
                return  date1.equals(date2);
            default:
                throw new IllegalArgumentException("不下确的数据类型");
        }
    }
    private boolean isNotModified(Object o1, Object o2, String objectName, Map map) {
        boolean isNotModified = isNotModified(o1, o2);
        if (!isNotModified) {
            //被修改
            map.put(objectName, "3");
        }
        return  isNotModified;
    }

    @Override
    public List<Map<String, Object>> quoteVersionCompare(QuoteMoldProd curQuoteMoldProd, QuoteMoldProd hisQuoteMoldProd) {
        Map<String, Object> map = null;
        //List<Map<String, Object>> prodMapList = null;

        List<Map<String, Object>> mapList = new ArrayList<>();
        if (curQuoteMoldProd == null) {
            if (hisQuoteMoldProd == null) {
                return mapList;
            } else {
                try {
                    map = BeanToMapUtil.convertBean(hisQuoteMoldProd);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
                map = StringUtils.mapCamelToUnderline(map);
                map.put("prod_id", hisQuoteMoldProd.getId());
                //2被删除
                map.put("modify_flag", "2");
                mapList.add(map);
            }
        } else if (hisQuoteMoldProd == null) {
            try {
                map = BeanToMapUtil.convertBean(curQuoteMoldProd);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
            map = StringUtils.mapCamelToUnderline(map);
            map.put("prod_id", curQuoteMoldProd.getId());
            //1新增
            map.put("modify_flag", "1");
            mapList.add(map);
        } else {
            try {
                map = BeanToMapUtil.convertBean(curQuoteMoldProd);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
            map = StringUtils.mapCamelToUnderline(map);
            map.put("modify_flag", "0");

            //判断是未修改还是被修改
            boolean isLineNotModified = true;
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMoldProd.getCstProdName(), hisQuoteMoldProd.getCstProdName(), "cst_prod_name_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMoldProd.getCstProdNo(), hisQuoteMoldProd.getCstProdNo(), "cst_prod_no_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMoldProd.getCavityTotal(), hisQuoteMoldProd.getCavityTotal(), "cavity_total_flag", map);
            if (!isLineNotModified) {
                //3被修改
                map.put("modify_flag", "3");
            }
            mapList.add(map);
            if (!isLineNotModified) {
                try {
                    map = BeanToMapUtil.convertBean(hisQuoteMoldProd);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
                map = StringUtils.mapCamelToUnderline(map);
                map.put("prod_id", hisQuoteMoldProd.getId());
                //4修改前
                map.put("modify_flag", "4");
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<QuoteMoldProd> selectCurrentAndPrevVersionQuoteMoldProds(Long quoteLineId, Integer hisVersion) {
        Map<String, Object> map = new HashMap<>();
        map.put("quoteLineId", quoteLineId);
        map.put("version", hisVersion);
        return quoteMoldProdMapper.selectCurrentAndPrevVersionQuoteMoldProds(map);
    }

    @Override
    public  List<Map<String, Object>> quoteVersionCompare(QuoteMold curQuoteMold, QuoteMold hisQuoteMold) {
        Map<String, Object> map = null;
        List<Map<String, Object>> prodMapList = null;

        List<Map<String, Object>> mapList = new ArrayList<>();
        if (curQuoteMold == null) {
            if (hisQuoteMold == null) {
                return mapList;
            } else {
                try {
                    map = BeanToMapUtil.convertBean(hisQuoteMold);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
                map = StringUtils.mapCamelToUnderline(map);
                map.put("quote_line_id", hisQuoteMold.getId());
                try {
                    prodMapList = StringUtils.camelToUnderline(BeanToMapUtil.convertBeanList(quoteMoldProdMapper.selectByQuoteLineIdAndVer(hisQuoteMold.getId(), hisQuoteMold.getVersion())));
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
                map.put("prod_list", prodMapList);
                //2被删除
                map.put("modify_flag", "2");
                mapList.add(map);
            }
        } else if (hisQuoteMold == null) {
            try {
                map = BeanToMapUtil.convertBean(curQuoteMold);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
            map = StringUtils.mapCamelToUnderline(map);
            map.put("quote_line_id", curQuoteMold.getId());
            try {
                prodMapList = StringUtils.camelToUnderline(BeanToMapUtil.convertBeanList(quoteMoldProdMapper.selectByQuoteLineId(curQuoteMold.getId())));
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
            map.put("prod_list", prodMapList);
            //1新增
            map.put("modify_flag", "1");
            mapList.add(map);
        } else {
            try {
                map = BeanToMapUtil.convertBean(curQuoteMold);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
            map = StringUtils.mapCamelToUnderline(map);
            map.put("modify_flag", "0");

            //判断是未修改还是被修改
            boolean isLineNotModified = true;
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getMaterial(), hisQuoteMold.getMaterial(), "material_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getType(), hisQuoteMold.getType(), "type_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getWaterType(), hisQuoteMold.getWaterType(), "water_type_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getRunner(), hisQuoteMold.getRunner(), "runner_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getOutWay(), hisQuoteMold.getOutWay(), "out_way_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getSurfaceReq(), hisQuoteMold.getSurfaceReq(), "surface_req_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getTaskTime(), hisQuoteMold.getTaskTime(), "task_time_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getLength(), hisQuoteMold.getLength(), "length_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getWidth(), hisQuoteMold.getWidth(), "width_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getHeight(), hisQuoteMold.getHeight(), "height_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getSizeUnit(), hisQuoteMold.getSizeUnit(), "size_unit_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getMoldMat(), hisQuoteMold.getMoldMat(), "mold_mat_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getCount(), hisQuoteMold.getCount(), "count_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getWeight(), hisQuoteMold.getWeight(), "weight_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getUpLocal(), hisQuoteMold.getUpLocal(), "up_local_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getUp(), hisQuoteMold.getUp(), "up_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getPriceLocal(), hisQuoteMold.getPriceLocal(), "price_local_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getPrice(), hisQuoteMold.getPrice(), "price_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getRebate(), hisQuoteMold.getRebate(), "rebate_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getRebatePrice(), hisQuoteMold.getRebatePrice(), "rebate_price_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getUpTax(), hisQuoteMold.getUpTax(), "up_tax_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getUpLocalTax(), hisQuoteMold.getUpLocalTax(), "up_local_tax_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getPriceTax(), hisQuoteMold.getPriceTax(), "price_tax_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getPriceLocalTax(), hisQuoteMold.getPriceLocalTax(), "price_local_tax_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getRebatePriceTax(), hisQuoteMold.getRebatePriceTax(), "rebate_price_tax_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getIntMode(), hisQuoteMold.getIntMode(), "int_mode_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getPriceTaxInt(), hisQuoteMold.getPriceTaxInt(), "price_tax_int_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getPriceLocalTaxInt(), hisQuoteMold.getPriceLocalTaxInt(), "price_local_tax_int_flag", map);
            isLineNotModified = isLineNotModified && isNotModified(curQuoteMold.getRebatePriceTaxInt(), hisQuoteMold.getRebatePriceTaxInt(), "rebate_price_tax_int_flag", map);

            //比较行产品所属的制品信息
            List<QuoteMoldProd> quoteMoldProdList = this.selectCurrentAndPrevVersionQuoteMoldProds(curQuoteMold.getId(), hisQuoteMold.getVersion());
            Integer len = quoteMoldProdList.size();
            QuoteMoldProd firQuoteMoldProd = null, secQuoteMoldProd = null, quoteMoldProd = null;
            Integer curVer = curQuoteMold.getVersion(), hisVer = hisQuoteMold.getVersion();
            ArrayList retList = new ArrayList<>();
            List<Map<String, Object>> compareList;
            for (int index = 0; index < len; index ++) {
                quoteMoldProd = quoteMoldProdList.get(index);
                if (firQuoteMoldProd == null) {
                    firQuoteMoldProd = quoteMoldProd;
                } else {
                    secQuoteMoldProd = quoteMoldProd;
                }
                if (secQuoteMoldProd == null) continue;

                if (firQuoteMoldProd.getVersion().equals(curVer)) {
                    //secQuoteMold = quoteMoldList.get(index + 1);
                    if (secQuoteMoldProd.getVersion().equals(hisVer)) {
                        if (firQuoteMoldProd.getId().equals(secQuoteMoldProd.getId())) {
                            compareList = this.quoteVersionCompare(firQuoteMoldProd, secQuoteMoldProd); //未修改，被修改
                            retList.add(compareList);
                            firQuoteMoldProd = secQuoteMoldProd = null;

                        } else {
                            compareList = this.quoteVersionCompare(firQuoteMoldProd, null); //新增
                            retList.add(compareList);
                            compareList = this.quoteVersionCompare(null, secQuoteMoldProd); //被删除
                            retList.add(compareList);
                            firQuoteMoldProd = secQuoteMoldProd = null;
                        }
                        index ++;
                    } else {
                        compareList = this.quoteVersionCompare(firQuoteMoldProd, null); //新增
                        retList.add(compareList);
                        firQuoteMoldProd = secQuoteMoldProd;
                        secQuoteMoldProd = null;
                    }
                } else {
                    compareList = this.quoteVersionCompare(null, secQuoteMoldProd); //被删除
                    retList.add(compareList);
                    firQuoteMoldProd = secQuoteMoldProd;
                    secQuoteMoldProd = null;
                }
            }
            if (firQuoteMoldProd != null) {
                if (firQuoteMoldProd.getVersion().equals(curVer)) {
                    compareList = this.quoteVersionCompare(firQuoteMoldProd, null); //新增
                    retList.add(compareList);
                } else {
                    compareList = this.quoteVersionCompare(null, secQuoteMoldProd); //被删除
                    retList.add(compareList);
                }
            }
            //-----------------------------------
            map.put("prod_list", retList);
            boolean isNotModifiedProds = true;
            for(Object o1: retList) {
                Map<String, Object> prodMap = (Map)o1;
                if ("1".equals(prodMap.get("modify_flag")) || "2".equals(prodMap.get("modify_flag")) || "3".equals(prodMap.get("modify_flag"))) {
                    isNotModifiedProds = false;
                }
            }
            if (!isNotModifiedProds) {
                map.put("prod_list_flag", "3");
            }
            isLineNotModified = isLineNotModified && isNotModifiedProds;
            /////////////////////////////////
            if (!isLineNotModified) {
                //3被修改
                map.put("modify_flag", "3");
            }
            mapList.add(map);
            if (!isLineNotModified) {
                try {
                    map = BeanToMapUtil.convertBean(hisQuoteMold);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
                map = StringUtils.mapCamelToUnderline(map);
                map.put("quote_line_id", hisQuoteMold.getId());
                try {
                    prodMapList = StringUtils.camelToUnderline(BeanToMapUtil.convertBeanList(quoteMoldProdMapper.selectByQuoteLineIdAndVer(hisQuoteMold.getId(), hisQuoteMold.getVersion())));
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
                map.put("prod_list", prodMapList);
                //4修改前
                map.put("modify_flag", "4");
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<QuoteMold> selectCurrentAndPrevVersionQuoteMolds(Long quoteId, Integer hisVersion) {
        Map<String, Object> map = new HashMap<>();
        map.put("quoteId", quoteId);
        map.put("version", hisVersion);
        return quoteMoldMapper.selectCurrentAndPrevVersionQuoteMolds(map);
    }

    @Override
    public List<Map<String, Object>> getQuoteVersionCompare(Long quoteId) {
        //return  null;

        Quote currQuote = this.selectQuoteByQuoteId(quoteId);
        if (currQuote == null) {
            throw new IllegalArgumentException("报价单不存在");
        }
        Quote hisQuote = this.selectQuoteByQuoteId(quoteId, currQuote.getVersion() - 1);
        if (hisQuote == null) {
            throw new IllegalArgumentException("报价单不存在历史版本");
        }
        QuoteMold firQuoteMold = null, secQuoteMold = null, quoteMold = null;

        Integer curVer = currQuote.getVersion();
        Integer hisVer = hisQuote.getVersion();

        ArrayList retList = new ArrayList<>();
        List<Map<String, Object>> compareList;
        ///将当前版本和上一版的产品信息综合在一起，并按id asc, version desc
        List<QuoteMold> quoteMoldList = this.selectCurrentAndPrevVersionQuoteMolds(quoteId, hisVer);
        Integer len = quoteMoldList.size();
        for (int index = 0; index < len; index ++) {
            quoteMold = quoteMoldList.get(index);
            if (firQuoteMold == null) {
                firQuoteMold = quoteMold;
            } else {
                secQuoteMold = quoteMold;
            }
            if (secQuoteMold == null) continue;

            if (firQuoteMold.getVersion().equals(curVer)) {
                //secQuoteMold = quoteMoldList.get(index + 1);
                if (secQuoteMold.getVersion().equals(hisVer)) {
                    if (firQuoteMold.getId().equals(secQuoteMold.getId())) {
                        compareList = this.quoteVersionCompare(firQuoteMold, secQuoteMold); //未修改，被修改
                        retList.add(compareList);
                        firQuoteMold = secQuoteMold = null;

                    } else {
                        compareList = this.quoteVersionCompare(firQuoteMold, null); //新增
                        retList.add(compareList);
                        compareList = this.quoteVersionCompare(null, secQuoteMold); //被删除
                        retList.add(compareList);
                        firQuoteMold = secQuoteMold = null;
                    }
                    index ++;
                } else {
                    compareList = this.quoteVersionCompare(firQuoteMold, null); //新增
                    retList.add(compareList);
                    firQuoteMold = secQuoteMold;
                    secQuoteMold = null;
                }
            } else {
                compareList = this.quoteVersionCompare(null, secQuoteMold); //被删除
                retList.add(compareList);
                firQuoteMold = secQuoteMold;
                secQuoteMold = null;
            }
        }
        if (firQuoteMold != null) {
            if (firQuoteMold.getVersion().equals(curVer)) {
                compareList = this.quoteVersionCompare(firQuoteMold, null); //新增
                retList.add(compareList);
            } else {
                compareList = this.quoteVersionCompare(null, secQuoteMold); //被删除
                retList.add(compareList);
            }
        }
        return  retList;
    }
}
