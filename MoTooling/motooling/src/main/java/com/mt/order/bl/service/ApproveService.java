package com.mt.order.bl.service;

import com.mt.common.customenum.RoleEnum;
import com.mt.order.common.model.ApproveRecordHis;

import java.util.List;
import java.util.Map;

public interface ApproveService {

    Map<String, Object> createApprove(Long billId, String billType, String approveStep, String approveFlag, String approveSugg, Long approveManId, String approveManName, StringBuilder err);

    ApproveRecordHis selectWaitApproveRecord(Long billId, String billType, String approveStep);

    List<ApproveRecordHis> selectApproveRecordsByApproveId(Long billId, String billType);

    Long getRoleId(Integer companyId, RoleEnum roleEnum);

    String getWorkNameByUid(Integer companyId, Long uid);

    boolean isHasPermission(Integer companyId, Long uid, RoleEnum roleEnum );
    }
