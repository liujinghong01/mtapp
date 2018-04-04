package com.mt.order.bl.service;

import java.util.Map;

public interface ProjectService {

    Map<String,Object> selectProjectList(Map<String, Object> param);

    boolean deleteProjectNo(Map<String, Object> param);

    Map<String, Object> updateAndAddModify(Map<String, Object> param);

    Map<String,Object> addCstOrderAuto(Map<String, Object> param);
    //查询客户订单头信息
    Map<String,Object> queryHead(Integer projectId);
    //查询客户定单下达列表
    Map<String,Object> orderStartupList(Map<String, Object> map);
    //参考客户订单历史
    Map<String,Object> addSelectProject(Map<String, Object> map);
}
