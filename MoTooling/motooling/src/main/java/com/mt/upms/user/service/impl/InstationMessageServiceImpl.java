package com.mt.upms.user.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.upms.common.dao.SysMessageMapper;
import com.mt.upms.common.dao.SysUseMessageMapper;
import com.mt.upms.common.model.SysMessage;
import com.mt.upms.common.model.SysUseMessage;
import com.mt.upms.user.service.InstationMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InstationMessageServiceImpl implements InstationMessageService {

    @Autowired
    private SysMessageMapper sysMessageMapper;

    @Autowired
    private SysUseMessageMapper sysUseMessageMapper;


    /**
     * //站内信（可群发）
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addSendMessage(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        List<Integer> receverIds = (List<Integer>) query.get("recever_uid");
        SysMessage sysMessage = new SysMessage();
        SysUseMessage sysUseMessage = new SysUseMessage();

        sysMessage.setContent(query.get("content").toString());
        sysMessage.setTitle(query.get("title").toString());
        sysMessage.setCreateTime(DateUtil.getCurrentDateTime());

        //把消息存入数据库
        sysMessageMapper.insert(sysMessage);
        //获取纯信息 表的自增ID 添加进
        sysUseMessage.setMessageId(sysMessage.getId());
        sysUseMessage.setSendUid(Integer.valueOf(map.get("uid").toString()));
        if(receverIds !=null && receverIds.size()>0){
            for (Integer ids: receverIds) {
                sysUseMessage.setReceverUid(ids);
                sysUseMessageMapper.insert(sysUseMessage);
            }
        }
        return null;
    }

    /**
     * 查看信息列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectMessage(Map<String, Object> map) {
        Integer messageType = Integer.valueOf(map.get("message_type").toString());
        if(messageType==1){
            map.put("send_uid",map.get("uid"));
        }else if(messageType==2){
            map.put("recever_uid",map.get("uid"));
        }
        List<Map<String, Object>> messageList=sysUseMessageMapper.selectMessageList(map);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("message_list",messageList);
        return returnMap;
    }
}
