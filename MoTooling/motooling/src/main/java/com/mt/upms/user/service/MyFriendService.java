package com.mt.upms.user.service;

import java.util.Map;

public interface MyFriendService {
    Map<String,Object> friendList(Long uId);

    Map<String,Object> friendView(Long uId, Integer friComId, Long friUid);
}
