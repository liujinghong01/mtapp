package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmTicketIncept;

public interface PmTicketInceptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmTicketIncept record);

    int insertSelective(PmTicketIncept record);

    PmTicketIncept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmTicketIncept record);

    int updateByPrimaryKey(PmTicketIncept record);

    ////////////////////////////////////////////

    PmTicketIncept selectByPopId(Long popId);
}