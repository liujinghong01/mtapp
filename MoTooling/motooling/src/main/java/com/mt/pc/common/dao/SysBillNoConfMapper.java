package com.mt.pc.common.dao;

import com.mt.pc.common.model.SysBillNoConf;

import java.util.Map;

public interface SysBillNoConfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysBillNoConf record);

    int insertSelective(SysBillNoConf record);

    SysBillNoConf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysBillNoConf record);

    int updateByPrimaryKey(SysBillNoConf record);
    //////////////////////////

    String createBillNo(Map param);
}