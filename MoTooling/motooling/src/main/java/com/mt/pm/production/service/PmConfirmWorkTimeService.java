package com.mt.pm.production.service;
/**
 * created by fhk on 2018.1.2
 *
 * 报价单参考及关联
 *
 */
import com.mt.pm.common.model.PmPgMember;

import java.util.List;
import java.util.Map;

public interface PmConfirmWorkTimeService {
    Map<String, Object> queryAllResourceByPopId(Long popId);

    List<Map<String, Object>>  selectTicketInceptList(Map map, StringBuilder totalCount);

    Object selectPgConfirmList(Map param, StringBuilder totalCount);

    List<Map<String, Object>> selectProcInfoByResourceId(Map map);

    List<Map<String, Object>> selectOneResourceCanProcessPop(Map map);

    int createAllocateResourceForPop(Map map, StringBuilder err);

    int createPopIncept(Map map, StringBuilder err);

    PmPgMember selectByPgIdAndMemberId(Long pgId, Long memberId);

    int modifyPopWorker(Map map, StringBuilder err);

    int updatePopStatus(Map map, StringBuilder err);

    Long[] getPgIdsByMemberId(Integer companyid, Long memberId) ;

    Long[] getPgIdsByDeviceId(Integer companyid, Long memberId);
}
