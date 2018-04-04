package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmPgMember;

import java.util.List;
import java.util.Map;

public interface PmPgMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmPgMember record);

    int insertSelective(PmPgMember record);

    PmPgMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmPgMember record);

    int updateByPrimaryKey(PmPgMember record);


    // 查看生产小组的详情（人员和公司的关系）
    List<Map<String,Object>>  selectProductionGroupInfo(Long pgId);

    // 人员删除
    Integer  updateMember(Map<String,Object>  map);

    // 人员新增
    Integer  addMemberInfo(Map<String, Object> map);

    // 人员修改
    Integer  updateMemberInfo(Map<String,Object> map);

    // 查询人员修改响应参数
    Map<String,Object>  selectMemberInfo(Map<String,Object>  map);

    // 查询人员新增响应参数
    List<Map<String,Object>>  selectMemberSaveInfo(Map<String,Object>  map);

    PmPgMember selectByPgIdAndMemberId(Map map);

    int updateIsLeader(Map map);
}