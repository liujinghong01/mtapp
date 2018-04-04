package com.mt.common.core.shiro.token.manager;


import com.mt.common.core.shiro.session.CustomSessionManager;
import com.mt.common.core.shiro.token.SampleRealm;
import com.mt.common.core.shiro.token.ShiroToken;
import com.mt.common.utils.SpringContextUtil;
import com.mt.upms.common.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 *
 * 
 * Shiro管理下的Token工具类
 */
public class TokenManager {
	//用户登录管理
	public static final SampleRealm realm = SpringContextUtil.getBean("sampleRealm",SampleRealm.class);
	//用户session管理
	public static final CustomSessionManager customSessionManager = SpringContextUtil.getBean("customSessionManager",CustomSessionManager.class);

	private static Logger logger = LoggerFactory.getLogger(TokenManager.class);

	/**
	 * 获取当前登录的用户User对象
	 * @return
	 */
	public static User getToken(){
		User token = (User)SecurityUtils.getSubject().getPrincipal();
		return token ;
	}

	/**
	 * 获取当前用户的Session
	 * @return
	 */
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	/**
	 * 获取当前用户NAME
	 * @return
	 */
	public static String getNickname(){
		return getToken().getUsername();
	}
	/**
	 * 获取当前用户ID
	 * @return
	 */
	public static Long getUserId(){
		return getToken()==null?null:getToken().getUid();
	}
	/**
	 * 把值放入到当前登录用户的Session里
	 * @param key
	 * @param value
	 */
	public static void setVal2Session(Object key ,Object value){
		getSession().setAttribute(key, value);
	}
	/**
	 * 从当前登录用户的Session里取值
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key){
		return getSession().getAttribute(key);
	}
	/**
	 * 获取验证码，获取一次后删除
	 * @return
	 */
	public static String getYZM(){
		String code = (String) getSession().getAttribute("CODE");
		getSession().removeAttribute("CODE");
		return code ;
	}
	
	
	/**
	 * 登录
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	public static User login(User user, Boolean rememberMe){
		String account = "";
		if(user.getEmail() != null && !user.getEmail().trim().equals("")) {
			account = user.getEmail().trim();
		} else if(user.getMobile() != null && !user.getMobile().trim().equals("")) {
			account = user.getMobile().trim();
		} else if(user.getWechat() != null && !user.getWechat().trim().equals("")) {
			account = user.getWechat().trim();
		} else {
			account = Long.toString(user.getUid());
		}
		//ShiroToken token = new ShiroToken(user.getEmail(), user.getPassword());
		ShiroToken token = new ShiroToken(account, user.getPassword(), user);
		token.setRememberMe(rememberMe);
		SecurityUtils.getSubject().login(token);
		Subject subject = SecurityUtils.getSubject();
		logger.debug(subject.getPrincipals().asList().toString());
		return getToken();
	}


	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}
	/**
	 * 退出登录
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	/**
	 * 清空当前用户权限信息。
	 * 目的：为了在判断权限的时候，再次会再次 <code>doGetAuthorizationInfo(...)  </code>方法。
	 * ps：	当然你可以手动调用  <code> doGetAuthorizationInfo(...)  </code>方法。
	 * 		这里只是说明下这个逻辑，当你清空了权限，<code> doGetAuthorizationInfo(...)  </code>就会被再次调用。
	 */
	public static void clearNowUserAuth(){
		/**
		 * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
		 */
		/**
		 * 获取当前系统的Realm的实例化对象，方法一（通过 @link org.apache.shiro.web.mgt.DefaultWebSecurityManager 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
		 * 获取到的时候是一个集合。Collection<Realm> 
			RealmSecurityManager securityManager =
		    			(RealmSecurityManager) SecurityUtils.getSecurityManager();
		  	SampleRealm realm = (SampleRealm)securityManager.getRealms().iterator().next();
		 */
		/**
		 * 方法二、通过ApplicationContext 从Spring容器里获取实列化对象。
		 */
		realm.clearCachedAuthorizationInfo();
		/**
		 * 当然还有很多直接或者间接的方法，此处不纠结。
		 */
	}
	
	
	
	
	/**
	 * 根据UserIds 	清空权限信息。
	 * @param id	用户ID
	 */
	public static void clearUserAuthByUserId(Long...userIds){
		
		if(null == userIds || userIds.length == 0)	return ;
		List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);
		
		for (SimplePrincipalCollection simplePrincipalCollection : result) {
			realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
		}
	}


	/**
	 * 方法重载
	 * @param userIds
	 */
	public static void clearUserAuthByUserId(List<Long> userIds) {
		if(null == userIds || userIds.size() == 0){
			return ;
		}
		clearUserAuthByUserId(userIds.toArray(new Long[0]));
	}
}
