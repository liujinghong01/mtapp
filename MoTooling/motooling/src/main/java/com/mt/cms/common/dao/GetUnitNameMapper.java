package com.mt.cms.common.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 根据 unit_code 查询 unit_name
 */
@Mapper
public interface GetUnitNameMapper {
    Map<String,String>  getUnitName(String code);

    List<Map<String,String>> selectCodeAndName();
}
