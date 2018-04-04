package com.mt.upms.common.dao;

import com.mt.upms.common.model.EmailSend;

public interface EmailSendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EmailSend record);

    int insertSelective(EmailSend record);

    EmailSend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EmailSend record);

    int updateByPrimaryKey(EmailSend record);

    // Added by fhk 2017.9.16
     EmailSend selectPasswordEmail(String message);

}