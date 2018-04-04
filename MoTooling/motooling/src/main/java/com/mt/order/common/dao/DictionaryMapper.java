package com.mt.order.common.dao;

import com.mt.order.common.model.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    //以下为自定义
    List<Map<String, Object>> selectDictList(Map<String, Object> map);

    String getDictNameByCode(String dictCode);
    //缺省部件列表
    List<Map<String,Object>> defaultPartList();
    //缺省子部件列表
    List<Map<String,Object>> defaultSubPartList(Map<String, Object> map );
    //发票种类 字典
    List<Map<String,Object>> selectInvoiceType(Map<String, Object> map);
    //物料规格型号列表
    List<Map<String,Object>> selectMatModel(Map<String, Object> map);
    //物料需求来源列表
    List<Map<String,Object>> selectMatRequreFrom(Map<String, Object> map);

    List<Map<String,Object>> selectRequireFrom();

    List<Map<String,Object>> selectInvoiceList(Integer companyId);
}