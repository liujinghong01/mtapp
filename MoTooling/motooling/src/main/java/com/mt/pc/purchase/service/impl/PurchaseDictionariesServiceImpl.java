package com.mt.pc.purchase.service.impl;

import com.mt.bm.common.dao.MatMapper;
import com.mt.bm.common.dao.MtMatTypeMapper;
import com.mt.cms.common.dao.SupplierMapper;
import com.mt.order.common.dao.DictionaryMapper;
import com.mt.pc.purchase.service.PurchaseDictionariesService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseDictionariesServiceImpl implements PurchaseDictionariesService {
    private final Logger logger = Logger.getLogger(PurchaseDictionariesServiceImpl.class);
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private MatMapper matMapper;
    @Autowired
    private MtMatTypeMapper mtMatTypeMapper;

    /**
     * 供应商列表
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectSupplierList(Map<String, Object> map) {
        Map<String, Object> param = (Map<String, Object>) map.get("query");
        param.put("companyId", map.get("companyId"));
        List<Map<String, Object>> nameList = supplierMapper.selectSupName(param);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("sup_list", nameList);
        return returnMap;
    }

    /**
     * 发票种类 字典
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectInvoiceType(Map<String, Object> map) {
        ////List<Map<String, Object>> invoiceList = dictionaryMapper.selectInvoiceType(map);
        List<Map<String, Object>> invoiceList = dictionaryMapper.selectInvoiceList(Integer.valueOf(map.get("companyId").toString()));

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list", invoiceList);
        return returnMap;
    }

    /**
     * 物料规格型号列表
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectMatModel(Map<String, Object> map) {
        List<Map<String, Object>> matModelList = dictionaryMapper.selectMatModel(map);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list", matModelList);
        return returnMap;
    }

    /**
     * 物料需求来源列表
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectMatRequreFrom(Map<String, Object> map) {
        List<Map<String, Object>> matRequreFromList = dictionaryMapper.selectMatRequreFrom(map);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list", matRequreFromList);
        return returnMap;
    }

    /**
     * 物料名称搜索 263
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectMatName(Map<String, Object> map) {
        Map<String, Object> param = new HashMap<>();
        param.put("company_id", map.get("companyId"));
        if (map.get("query") != null && map.get("query") != "") {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            param.put("mat_desc", query.get("mat_desc"));
            param.put("is_include_process_type", query.get("is_include_process_type").toString());
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("mat_list", matMapper.selectMatName(param));
        return returnMap;
    }

    /**
     * 根据供应商查询物料
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryMatBySupplier(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        List<Integer> supCompanyIds = (List<Integer>) query.get("sup_company_ids");
        List<Integer> param = null;
        if (supCompanyIds.size() > 1) {
            param = supplierMapper.getMatBySupplier(supCompanyIds);
            Set set = new HashSet();
            set.addAll(param);
            set.retainAll(param);
            param = (List<Integer>) CollectionUtils.disjunction(set, param);
        } else {
            param = supplierMapper.getMatBySupplier(supCompanyIds);
        }
        //查询物料
        Map<String, Object> typeMap = new HashMap<>();
        typeMap.put("param",param);
        typeMap.put("is_include_process_type",query.get("is_include_process_type"));
        List<Map<String, Object>> objectMap = mtMatTypeMapper.selectMtMat(typeMap);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("mat_list", objectMap);
        return returnMap;
    }

    @Override
    public Map<String, Object> querySupplierByMat(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        List<Integer> matIds = (List<Integer>) query.get("mat_ids");
        if (matIds.size()>0) {
            List<Map<String, Object>> list = mtMatTypeMapper.selectMatIds(matIds);
            if (list.size()<1){
               throw  new IllegalArgumentException("没有该物料信息");
            }
            List<Map<String, Object>> param = supplierMapper.selectSupplierList(list);
            for (Map<String, Object> objectMap : param) {
                List<Map<String, Object>> matList = supplierMapper.selectSupplierMat(objectMap);
                if (matList.size() >0 ) {
                    objectMap.put("connect_list",matList);
                }
            }
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("sup_list", param);
            return returnMap;
        } else {
            return null;
        }
    }


    /**
     * 物料需求来源列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectRequireFrom(Map<String, Object> map) {
        List<Map<String, Object>> list =  dictionaryMapper.selectRequireFrom();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list",list);
        return returnMap;
    }
}
