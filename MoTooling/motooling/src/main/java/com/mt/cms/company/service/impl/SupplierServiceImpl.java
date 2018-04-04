package com.mt.cms.company.service.impl;

import com.mt.cms.common.dao.SupplierMapper;
import com.mt.cms.common.model.Supplier;
import com.mt.cms.company.service.SupplierService;
import com.mt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mt.common.utils.StringUtils.camelToUnderline;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public Map<String, Object> selectsupplierId(Map<String, Object> map) {
        int totalCount = supplierMapper.totalCountIsActive(map);
        Integer curr_page = (Integer)map.get("curr_page");
        Integer page_size = (Integer)map.get("page_size");
        map.put("page",(curr_page - 1) * page_size);
        List<Map<String, Object>> list = supplierMapper.selectsupplierId(map);
        if(list.size()<1){
            return StringUtils.page(map, list,"cst_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list,"cst_info",totalCount);
        return page;
    }

    @Override
    public Map<String, Object> getSupplier(Map<String, Object> param) {
        Map<String, Object> mapReturn = new HashMap<>();
        Map<String, Object> map = supplierMapper.getSupplier(param);
        for (String m : map.keySet()) {
            mapReturn.put(camelToUnderline(m), map.get(m));
        }
        return mapReturn;
    }

    @Override
    public Supplier getSuppliers(Long cstCompanyId) {
        return supplierMapper.getSuppliers(cstCompanyId);
    }

    @Override
    @Transactional
    public int updateSupplier(Supplier supplier) {
        return supplierMapper.updateSupplier(supplier);
    }

    @Override
    @Transactional
    public int deleteSupplier( List<Integer> cst_sup_id) {
        return supplierMapper.deleteSupplier(cst_sup_id);
    }

    @Override
    public Map<String, Object> searchSupName(Map<String, Object> map) {
        Integer curr_page = (Integer) map.get("curr_page");
        Integer page_size = (Integer) map.get("page_size");
        map.put("page",(curr_page - 1) * page_size);
        List<Map<String, Object>> list = supplierMapper.searchSupName(map);
        int totalCount = supplierMapper.totalCount(map);
        if(list.size()<1){
            return StringUtils.page(map, list,"cst_sup",0);
        }
        Map<String, Object> page = StringUtils.page(map, list,"cst_sup", totalCount);
        return page;
    }

    @Override
    @Transactional
    public int insertSupplier(Supplier supplier) {
        return supplierMapper.insertSelective(supplier);
    }

    @Override
    public Map<String, Object> selectSupplier(Long supCompanyId) {
        Map<String, Object> mapReturn = new HashMap<>();
        Map<String, Object> map = supplierMapper.selectSupplier(supCompanyId);
        for (String m : map.keySet()) {
            mapReturn.put(camelToUnderline(m), map.get(m));
        }
        return mapReturn;
    }
}
