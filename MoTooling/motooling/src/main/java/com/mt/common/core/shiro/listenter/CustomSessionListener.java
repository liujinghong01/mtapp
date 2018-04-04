package com.mt.common.core.shiro.listenter;


import com.mt.common.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 
 * shiro 会话监听
 *
 * 
 */
public class CustomSessionListener implements SessionListener {
    private static Logger logger = LoggerFactory.getLogger(CustomSessionListener.class);

    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 一个会话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
        logger.info("会话开始       on start  {}",session.getId());
    }
    /**
     * 一个会话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        //TODO
        shiroSessionRepository.deleteSession(session.getId());
        logger.info("会话结束       on stop {}",session.getId());
    }

    /**
     * 一个会话的生命周期过期
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
        logger.info("会话过期       {}",session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}

