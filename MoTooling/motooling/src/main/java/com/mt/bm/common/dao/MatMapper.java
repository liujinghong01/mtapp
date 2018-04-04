package com.mt.bm.common.dao;

import com.mt.bm.common.model.Mat;

import java.util.List;
import java.util.Map;

public interface MatMapper {
    int deleteByPrimaryKey(Long matId);

    int insert(Mat record);

    int insertSelective(Mat record);

    Mat selectByPrimaryKey(Long matId);

    int updateByPrimaryKeySelective(Mat record);

    int updateByPrimaryKey(Mat record);

    //Alnwick 添加

    // /物料名称搜索
    List<Map<String,Object>> selectMatName(Map<String, Object> map);

    List<String> selectMatNameList(Map<String, Object> map);

    List<Map<String,Object>> selectMatNameSpecList(Map<String, Object> map);
    // 查看所有 物料类型
    List<Mat> selectBmMatAllMatName(Map<String, Object> map);
    //求总条数
    Integer selectBmMatAllMatNameTotalCount(Map<String, Object> map);
}