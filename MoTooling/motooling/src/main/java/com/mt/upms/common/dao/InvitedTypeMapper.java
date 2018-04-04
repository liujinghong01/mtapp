package com.mt.upms.common.dao;

import com.mt.upms.common.model.InvitedType;

public interface InvitedTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvitedType record);

    int insertSelective(InvitedType record);

    InvitedType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvitedType record);

    int updateByPrimaryKey(InvitedType record);
}