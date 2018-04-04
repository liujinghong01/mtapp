package com.mt.common.core.shiro.session;

import com.mt.upms.common.model.User;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * Session操作
 * 
 */
@Service
public interface ShiroSessionRepository {

	/**
	 * 存储Session
	 * @param session
	 */
    void saveSession(Session session);
    /**
     * 删除session
     * @param sessionId
     */
    void deleteSession(Serializable sessionId);
    /**
     * 获取session
     * @param sessionId
     * @return
     */
    Session getSession(Serializable sessionId);
    /**
     * 获取所有sessoin
     * @return
     */
    Collection<Session> getAllSessions();

    /**
     *  获取 session 中的用户信息
     */
    User getSessionUser(Serializable sessionId);
}
