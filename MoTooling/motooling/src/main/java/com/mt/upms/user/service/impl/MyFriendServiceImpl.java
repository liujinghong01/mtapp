package com.mt.upms.user.service.impl;

import com.mt.upms.common.dao.MyFriendMapper;
import com.mt.upms.user.service.MyFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyFriendServiceImpl implements MyFriendService {

    @Autowired
    private MyFriendMapper myFriendMapper;

    @Override
    public Map<String, Object> friendList(Long uId) {
        Map<String, Object> mapReturn = new HashMap();
        List<Map<String, Object>> map =  myFriendMapper.friendList(uId);
        mapReturn.put("friends",map);
        return mapReturn;
    }

    @Override
    public Map<String, Object> friendView(Long uId, Integer friComId, Long friUid) {
        Map<String, Object> paraMap = new HashMap();
        paraMap.put("uId",uId);
        paraMap.put("friComId",friComId);
        paraMap.put("friUid",friUid);
        Map<String, Object> map = myFriendMapper.friendView(paraMap);
        return map;
    }
}
