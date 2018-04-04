package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmTicketAllocate;

import java.util.List;
import java.util.Map;

public interface PmTicketAllocateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmTicketAllocate record);

    int insertSelective(PmTicketAllocate record);

    PmTicketAllocate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmTicketAllocate record);

    int updateByPrimaryKey(PmTicketAllocate record);

    /////////////////////////////////////////////////
    List<Map<String, Object>> selectAllDeviceByPopId(Long popId);

    List<Map<String, Object>> selectAllMemberByPopId(Long popId);

    int setDeleteStatusByPopId(Long popId);

    List<Map<String, Object>> selectAllocatedResByPopId(Long popId);
}