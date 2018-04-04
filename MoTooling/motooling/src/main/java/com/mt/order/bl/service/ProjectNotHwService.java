package com.mt.order.bl.service;

import java.util.Map;

public interface ProjectNotHwService {
    Map<String, Object> queryNotHwLineList(Map<String, Object> param);

    Map<String,Object> queryNotHwLineDetail(Integer param);

    Map<String,Object> addAndUpdatemodifyLine(Map<String, Object> param);
    //删除制品信息
    boolean deleteProd(Map<String, Object> param);
    //删除行信息
    boolean deleteLine(Map<String, Object> param);

    boolean updateOrderStartup(Integer orderLineId);
    //设置客户定单产品反完结
    boolean updateOrderNotEnd(Integer orderLineId);
    //设置客户定单产品暂停或者完结
    boolean updateOrderStartupStop(Map<String, Object> param);
}
