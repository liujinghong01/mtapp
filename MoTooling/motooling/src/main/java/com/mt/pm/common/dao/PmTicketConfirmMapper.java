package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmTicketConfirm;

import java.util.List;
import java.util.Map;

public interface PmTicketConfirmMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmTicketConfirm record);

    int insertSelective(PmTicketConfirm record);

    PmTicketConfirm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmTicketConfirm record);

    int updateByPrimaryKey(PmTicketConfirm record);

    ///////////////////////////////////////////////

    Long getTotalCountByConditions(Map map);
    List<Map<String, Object>> selectByConditions(Map map);

    List<Map<String, Object>>  selectMyCanProcessPop(Map map);
    Long  getMyCanProcessPopCount(Map map);

    List<Map<String, Object>>  selectMyAllocatedPop(Map map);

    List<Map<String, Object>>  selectMyDeviceAllocatedPop(Map map);
    List<Map<String, Object>>  selectOneDeviceAllocatedPop(Map map);

    List<Map<String, Object>>  selectPgUnAllocatedPop(Map map);

    List<Map<String, Object>>  getMyPgDevice(Map map);
    Long getMyPgDeviceCount(Map param);

    List<Map<String, Object>>  getMyPgMember(Map map);
    Long getMyPgMemberCount(Map param);

    List<Map<String, Object>> selectProcInfoByResourceId(Map map);

    List<Map<String, Object>>  selectOneResourceCanProcessPop(Map map);

    PmTicketConfirm selectByPopIdAndMemberId(Map map);

    List<PmTicketConfirm> selectByPopId(Map map);

    Long getMaxBatchProcId();

    Long getProcessingBatchProcIdByPopId(Long popId);

    List<PmTicketConfirm> selectProcessingByBatchProcId(Long batchProcId);
}
