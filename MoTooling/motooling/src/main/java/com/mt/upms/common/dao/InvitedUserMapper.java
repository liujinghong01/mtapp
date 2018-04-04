package com.mt.upms.common.dao;

import com.mt.upms.common.model.InvitedUser;

public interface InvitedUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvitedUser record);

    int insertSelective(InvitedUser record);

    InvitedUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvitedUser record);

    int updateByPrimaryKey(InvitedUser record);
}