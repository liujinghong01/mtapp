package com.mt.pm.production.service;

import com.mt.pm.common.model.*;

import java.util.List;
import java.util.Map;

public interface PmProcessService {

    //基础工序
    boolean deleteProcessByPrimaryKey(Long id);

    boolean createOrUpdateProcess(PmProcConf record,List<PmProcPg> records);

    PmProcConf selectProcessByPrimaryKey(Long id);

    Map<String, Object> selectProcessByConditions(Map<String, Object> map);


   //工艺模板类型
    boolean deleteRoutingTmplTypeByPrimaryKey(Long id);

    boolean createOrUpdatePmRoutingTmplType(PmRoutingTmplType record);

    PmProcConf selectRoutingTmplTypeByPrimaryKey(Long id);

    Map<String, Object> selectRoutingTmplTypeByConditions(Map<String, Object> map);

    List<Map<String, Object>> selectRoutingTmplTypeList(Map queryMap);


    //工艺模板
    boolean deleteRoutingTmplByPrimaryKey(Long id);

    boolean createOrUpdatePmRoutingTmpl(PmRoutingTmpl record,List<PmRoutingTmplSub> records);

    PmRoutingTmpl selectRoutingTmplByPrimaryKey(Long id);

    Map<String, Object> selectRoutingTmplByConditions(Map<String, Object> map);

}
