package com.mt.order.bl.service;

import java.util.Map;

public interface ComputeService {
    Map<String,Object> selectOrderList(Map<String, Object> map);

    Map<String,Object> addCustomerOrder(Map<String, Object> map) throws IllegalAccessException;

    Map<String,Object> selectOrderMoldDatail(Integer compute_id);

    Boolean deleteOrder(Long computeId);

    Map<String,Object> selectassciate(Map<String, Object> map)throws IllegalAccessException;

}
