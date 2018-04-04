package com.mt.order.common.dao;

import com.mt.order.common.model.ApproveRecordHis;

import java.util.List;
import java.util.Map;

public interface ApproveRecordHisMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApproveRecordHis record);

    int insertSelective(ApproveRecordHis record);

    ApproveRecordHis selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApproveRecordHis record);

    int updateByPrimaryKey(ApproveRecordHis record);

    //以下为自定义
    ApproveRecordHis selectWaitApproveRecord(Map<String, Object> map);

    List<ApproveRecordHis> selectApproveRecordsByApproveId(Map<String, Object> map);
}