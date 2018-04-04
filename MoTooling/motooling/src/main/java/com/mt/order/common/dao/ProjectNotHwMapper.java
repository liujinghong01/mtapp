package com.mt.order.common.dao;

import com.mt.order.common.model.ProjectNotHw;

import java.util.List;
import java.util.Map;

public interface ProjectNotHwMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectNotHw record);

    int insertSelective(ProjectNotHw record);

    ProjectNotHw selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectNotHw record);

    int updateByPrimaryKey(ProjectNotHw record);
    //////////////////////////////////////////////

    Map<String,Object> selectQuote(Map<String, Object> param);

    int addNotHw(Map<String, Object> param);

    List<Map<String, Object>> queryNotHwLineListMap(Map<String, Object> param);

    List<Map<String, Object>> queryNotHwLineList(Integer projectId);

    Map<String,Object> queryNotHwLineDetail(Integer param);

    int updateNotHw(Map<String, Object> map);

    int insterNotHw(Map<String, Object> map);

    boolean deleteLine(Integer i);
    //查询客户定单下达列表
    List<Map<String,Object>> orderStartupList(Map<String, Object> map);

    Integer totalCount(Map<String, Object> map);

    //设置客户定单产品开始
    boolean orderStartup(Integer orderLineId);
    //设置客户定单产品反完结
    boolean orderNotEnd(Integer orderLineId);
    //设置客户定单产品暂停或者完结
    boolean orderStartupStop(Map<String, Object> param);

    Map<String,Object> selectProject(Map<String, Object> param);

    Integer getCompanyIdByProjectNotHwId(Long id);
}
