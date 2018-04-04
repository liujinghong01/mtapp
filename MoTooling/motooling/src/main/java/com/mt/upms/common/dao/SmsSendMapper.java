package com.mt.upms.common.dao;

import com.mt.upms.common.model.SmsSend;

import java.util.List;

public interface SmsSendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmsSend record);

    SmsSend selectByPrimaryKey(Long id);

    List<SmsSend> selectAll();

    int updateByPrimaryKey(SmsSend record);

    /**
     * added  by fhk on 2017.9.13
     */
    int createLoginSMS(SmsSend smsSend);
    SmsSend selectByPhone(String phone);
    int deleteByPhone(String phone);
}
