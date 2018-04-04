package com.mt.upms.common.dao;

import com.mt.upms.common.model.MyFriend;

import java.util.List;
import java.util.Map;

public interface MyFriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MyFriend record);

    int insertSelective(MyFriend record);

    MyFriend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MyFriend record);

    int updateByPrimaryKey(MyFriend record);

    ///////////////////////////////////////////////
    List<Map<String,Object>> friendList(Long uId);

    Map<String,Object> friendView(Map<String, Object> paraMap);

    MyFriend selectMyFriend(Map<String, Object>Map);

    List<Long> seletUId(Integer uId);
}