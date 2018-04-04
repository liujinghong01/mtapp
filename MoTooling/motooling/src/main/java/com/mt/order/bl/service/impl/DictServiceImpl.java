package com.mt.order.bl.service.impl;

import com.mt.order.bl.service.DictService;
import com.mt.order.common.dao.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by fhk on 2017.10.24
 * 数据字典表服务
 */

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictionaryMapper dictionaryMapper;


    @Override
    public List<Map<String, Object>> selectDictList(Integer companyId, String dictType) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("dictType", dictType);
        return dictionaryMapper.selectDictList(map);
    }

    @Override
    public void getDictNameByCode(Map<String, Object> map, String codeColName, String nameColName) {
        map.put(nameColName, dictionaryMapper.getDictNameByCode(map.get(codeColName) == null ? null : map.get(codeColName).toString().trim()));
    }

    @Override
    public void getDictNameByCode(List<Map<String, Object>> mapList, String codeColName, String nameColName) {
        for (Map<String, Object> map: mapList) {
            map.put(nameColName, dictionaryMapper.getDictNameByCode(map.get(codeColName) == null ? null : map.get(codeColName).toString().trim()));
        }
    }

    @Override
    public  List<Map<String, Object>> selectInvoiceList(Integer companyId) {
        return dictionaryMapper.selectInvoiceList(companyId);
    }

}
