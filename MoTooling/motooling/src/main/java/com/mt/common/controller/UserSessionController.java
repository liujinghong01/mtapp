package com.mt.common.controller;

import com.mt.common.core.shiro.session.ShiroSessionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Alnwick
 * @Date: create in 2018-02-27   11:14
 * @Description: 对 session 缓存 用户进行操作。
 * @Modified By:
 */
@RestController
@Api(value = "缓存操作", description = "缓存操作")
public class UserSessionController {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    ShiroSessionRepository shiroSessionRepository;

    @PostMapping("api/session/get_all_sessions")
    @ApiOperation(value = "查看全部缓存对应的账号", notes = "")
    public Object getAllSessions() {
        Collection<Session> allSessions = shiroSessionRepository.getAllSessions();
        return allSessions;
    }

    @PostMapping("api/session/get_all_session_ids")
    @ApiOperation(value = "查看全部缓存ID", notes = "")
    public Object getAllSessionIds() {
        Collection<Session> allSessions = shiroSessionRepository.getAllSessions();
        Iterator<Session> iterator = allSessions.iterator();
        HashSet<Serializable> set = new HashSet();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            set.add(session.getId());
        }
        return set;
    }

    @PostMapping("api/session/del_session_id")
    @ApiOperation(value = "删除缓存", notes = "")
    public Object deleteSessions(@RequestBody Map<String, Object> param) {
        String sessionId = param.get("sessionId").toString();
        shiroSessionRepository.deleteSession(sessionId);
        return "OK";
    }


    @PostMapping("api/session/del_session_list")
    @ApiOperation(value = "删除全部缓存", notes = "")
    public Object deleteAllSessions() {
        Collection<Session> allSessions = shiroSessionRepository.getAllSessions();
        Iterator<Session> iterator = allSessions.iterator();
        HashSet<Serializable> set = new HashSet();
        try {
            while (iterator.hasNext()) {
                Session session = iterator.next();
                set.add(session.getId());
            }
            Iterator<Serializable> setIterator = set.iterator();
            while (setIterator.hasNext()) {
                shiroSessionRepository.deleteSession(setIterator.next());
            }
            return "OK";
        } catch (Exception e) {
            e.getMessage();
            return "no";
        }
    }

    /**
     * TODO
     * 踢人
     * @param map
     * @return
     */
    @PostMapping("api/session/forceLogout")
    public Object forceLogout(@RequestBody Map<String, Object> map) {
        try {
            Session session = sessionDAO.readSession(map.get("sessionId").toString());
            if (session != null) {

            }
            return "logout  : success";
        } catch (Exception e) {
            e.getStackTrace();
            return "logout  :   false";
        }

    }

}
