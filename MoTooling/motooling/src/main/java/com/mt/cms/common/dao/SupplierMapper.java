package com.mt.cms.common.dao;

import com.mt.cms.common.model.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    //////////////////////////////////////////
    List<Map<String,Object>> selectsupplierId(Map<String,Object> map);

    Map<String, Object> getSupplier(Map<String, Object> param);

    int updateSupplier(Supplier supplier);

    int deleteSupplier( List<Integer> list);

    List<Map<String,Object>> searchSupName(Map<String, Object> map);

    int totalCount(Map<String, Object> map);

    Integer getMaxSupplierId();

    Map<String,Object> selectSupplier(Long supCompanyId);

    Supplier getSuppliers(Long cstCompanyId);

    Supplier selectByCompanyIdAndSupplierId(Map map);

    List<Long>  seletComapyId(int compayId);

    int totalCountIsActive(Map<String, Object> map);

    //字典 根据名字模糊查询供应商
    List<Map<String,Object>> selectSupName(Map<String, Object> map);
    //根据供应商查询物料
    List<Integer> getMatBySupplier(List<Integer> list);

    List<Map<String,Object>> selectSupplierList( List list);

    List<Map<String,Object>> selectSupplierMat(Map<String, Object> objectMap);
}