package com.mt.upms.user.service;

import java.util.Map;

public interface InstationMessageService {
    //站内信（可群发）
    Map<String,Object> addSendMessage(Map<String, Object> map);
    //查看信息列表
    Map<String,Object> selectMessage(Map<String, Object> map);
}
