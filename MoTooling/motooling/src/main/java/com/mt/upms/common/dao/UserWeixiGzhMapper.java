package com.mt.upms.common.dao;

import java.util.List;
import java.util.Map;

public interface UserWeixiGzhMapper {
     int addserWeixiGzh(Map<String,Object> map);

     int updateUserWeixiGzh(Map<String,Object> map);

    //获取 open_id 集合
    List<String> selectOpenIdList();

    String selectOpenId(String unionId);
}
