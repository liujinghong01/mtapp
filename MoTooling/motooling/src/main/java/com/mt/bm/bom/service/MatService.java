package com.mt.bm.bom.service;

import com.mt.bm.common.model.Mat;

import java.util.List;
import java.util.Map;

public interface MatService {
    int deleteByPrimaryKey(Long matId);

    int insert(Mat record);

    int insertSelective(Mat record);

    Mat selectByPrimaryKey(Long matId);

    int updateByPrimaryKeySelective(Mat record);

    int updateByPrimaryKey(Mat record);

    List<String> selectMatNameList(Map<String, Object> map);

    List<Map<String,Object>> selectMatNameSpecList(Map<String, Object> map);

}
