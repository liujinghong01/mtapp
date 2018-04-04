package com.mt.cms.company.service;

import com.mt.cms.common.model.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    Map<String,Object> selectsupplierId( Map<String,Object> map);


    Map<String, Object> getSupplier(Map<String, Object> map);


    Supplier getSuppliers(Long cstCompanyId);


    int updateSupplier(Supplier supplier);


    int deleteSupplier( List<Integer> cst_sup_id);


    Map<String,Object> searchSupName(Map<String,Object> map);


    int insertSupplier(Supplier supplier);


    Map<String,Object> selectSupplier(Long supCompanyId);

}
