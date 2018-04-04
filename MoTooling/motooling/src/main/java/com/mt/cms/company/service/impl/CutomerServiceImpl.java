package com.mt.cms.company.service.impl;

import com.mt.cms.common.dao.CustomerMapper;
import com.mt.cms.common.model.Customer;
import com.mt.cms.company.service.CustomerService;
import com.mt.common.core.mybatis.BaseMybatisDao;
import com.mt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mt.common.utils.StringUtils.camelToUnderline;

@Service
public class CutomerServiceImpl extends BaseMybatisDao<CustomerMapper> implements CustomerService {
    @Autowired
    private  CustomerMapper customerMapper;

    @Override
    public Map<String, Object> selectCustomerId(Map<String, Object> map) {
        int totalCount  = customerMapper.totalCountIsActive(map);
        Integer curr_page = (Integer) map.get("curr_page");
        Integer page_size = (Integer) map.get("page_size");
        map.put("page",(curr_page - 1) * page_size);
        List<Map<String, Object>> list = customerMapper.selectCustomerId(map);
        if(list.size()<1){
            return StringUtils.page(map, list,"cst_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list,"cst_info", totalCount);
        return page;
    }

    @Override
    public  Map<String, Object> getCustome( Map<String, Object> param) {
        Map<String,Object> map = customerMapper.getCustome(param);
        Map<String, Object> mapReturn = new HashMap<>();
        for (String m : map.keySet()) {
            mapReturn.put(camelToUnderline(m),map.get(m));
        }
        return mapReturn;
    }

    @Override
    public Customer getCustomes(Long cstCompanyId) {
        return customerMapper.getCustomes(cstCompanyId);
    }

    @Override
    @Transactional
    public int updateCustomer(Customer customer) {
        return customerMapper.updateCustomer(customer);
    }

    @Override
    @Transactional
    public int deleteCustomer( List<Integer> cst_sup_id) {
        return customerMapper.deleteCustomer(cst_sup_id);
    }

    @Override
    public Map<String, Object> searchCstName(Map<String, Object> map) {
        Integer curr_page = (Integer) map.get("curr_page");
        Integer page_size = (Integer) map.get("page_size");
        map.put("page",(curr_page - 1) * page_size);
        List<Map<String, Object>> list = customerMapper.seachCstName(map);
        int totalCount = customerMapper.totalCount(map);
        if(list.size()<1){
            return StringUtils.page(map, list,"cst_sup",0);
        }
        Map<String, Object> page = StringUtils.page(map, list,"cst_sup", totalCount);
        return page;
    }

    @Override
    @Transactional
    public int insertCustomer(Customer customer) {
        return customerMapper.insertSelective(customer);
    }

    @Override
    public Map<String, Object> selectCustomer(Long companyId, Long cstCompanyId) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("companyId", companyId);
        mapParam.put("cstCompanyId", cstCompanyId);
        Map<String, Object> map = customerMapper.selectCustomer(mapParam);

        Map<String, Object> mapReturn = new HashMap<>();
        for (String m : map.keySet()) {
            mapReturn.put(camelToUnderline(m),map.get(m));
        }
        return mapReturn;
    }
    @Override
    public Map<String, Object> customerList(Map<String, Object> map) {
        List< Map<String, Object>> listMap =  customerMapper.customerList(map);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("cst_list",listMap);
        return returnMap;
    }
}
