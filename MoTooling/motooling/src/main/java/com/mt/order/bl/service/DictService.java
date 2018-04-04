package com.mt.order.bl.service;


import java.util.List;
import java.util.Map;

public interface DictService {

    List<Map<String, Object>> selectDictList(Integer companyId, String dictType);

    void getDictNameByCode(Map<String, Object> map, String codeColName, String nameColName);

    void getDictNameByCode(List<Map<String, Object>> mapList, String codeColName, String nameColName);

    List<Map<String, Object>> selectInvoiceList(Integer companyId);


}
