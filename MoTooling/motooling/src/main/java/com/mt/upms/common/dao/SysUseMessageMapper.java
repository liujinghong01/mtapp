package com.mt.upms.common.dao;

import com.mt.upms.common.model.SysUseMessage;

import java.util.List;
import java.util.Map;

public interface SysUseMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUseMessage record);

    int insertSelective(SysUseMessage record);

    SysUseMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUseMessage record);

    int updateByPrimaryKey(SysUseMessage record);
    //查询信息列表
    List<Map<String,Object>> selectMessageList(Map<String, Object> map);
}