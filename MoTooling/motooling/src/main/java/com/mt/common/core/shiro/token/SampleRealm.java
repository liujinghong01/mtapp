package com.mt.common.core.shiro.token;


import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.utils.RedisUtil;
import com.mt.upms.common.model.User;
import com.mt.upms.permission.service.PermissionService;
import com.mt.upms.permission.service.RoleService;
import com.mt.upms.user.service.UserService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * 
 * shiro 认证 + 授权   重写
 *
 * 
 */
public class SampleRealm extends AuthorizingRealm {

	@Autowired
	UserService userService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	RoleService roleService;

	private static final String OR_OPERATOR = "or";
	private static final String AND_OPERATOR = "and";
	private static final String NOT_OPERATOR = "not";
	
	public SampleRealm() {
		super();
	}
	/**
	 *  认证信息，主要针对用户登录， 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		ShiroToken token = (ShiroToken) authcToken;
		//User user = userService.login(token.getUsername(),token.getPswd());
		User user = token.getTokenUser();
		if(null == user){
			throw new AccountException("帐号或密码不正确！");
		/**
		 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
		 */
		/*}else if(User._0.equals(user.getStatus())){
			throw new DisabledAccountException("帐号已经禁止登录！");*/
		}else{
			//更新登录时间 last login time
			user.setLastLoginTime(new Date());
			userService.updateByPrimaryKeySelective(user);
		}
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

	 /** 
     * 授权 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取当前登陆对象
		User user = TokenManager.getToken();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		String companyId =null;
		Set<String> roles = new HashSet<>();
		Set<String> permissions = new HashSet<>();

		//从redis中 取出Json 字符串
		String userRole = RedisUtil.getMapString("user_role_permission", "role_permissions_uid_" + user.getUid()+"_company_id_"+user.getCompanyId());
//		String userRole = RedisUtil.getMapString("user_role_permission", "role_permissions_uid_" + user.getUid());
		if(userRole!=null){
			JSONObject jsonObject = JSONObject.fromObject(userRole);
			List<Map<String,String>> roleList = (List<Map<String, String>>) jsonObject.get("role");
			for (Map<String,String> map: roleList) {
				roles.add(map.get("code"));
			}
			permissions.addAll((Collection<? extends String>) jsonObject.get("permissions"));
			companyId= jsonObject.get("company_id").toString();
		}

		if(companyId!=null && companyId != "") {
			user.setCompanyId(Long.valueOf(companyId));
			//根据用户ID查询角色（role），放入到Authorization里。
//			Set<String> roles = roleService.findRoleByUserId(user);
			info.setRoles(roles);
			//根据用户ID查询权限（permission），放入到Authorization里。
//			Set<String> permissions = permissionService.findPermissionByUserId(user);
			info.setStringPermissions(permissions);
		}
		return info;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission){
    	//TODO 是否还需要截取？  shiro 注解已有 and 和or
//    	if (permission.contains(OR_OPERATOR)){
//    		String[] permissions = permission.split(OR_OPERATOR);
//    		for (String orPermission : permissions){
//    			if (isPermittedWithNotOperator(principals, orPermission)){
//    				return true;
//				}
//			}
//			return false;
//		}else
// 		if (permission.contains(AND_OPERATOR)){
//    		String[] permissions = permission.split(AND_OPERATOR);
//    		for (String orPermission : permissions){
//    			if (!isPermittedWithNotOperator(principals, orPermission)){
//    				return false;
//				}
//			}
//			return true;
//		}else {
    		return isPermittedWithNotOperator(principals, permission);
//		}
	}

	private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
    	if (permission.startsWith(NOT_OPERATOR)){
    		return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
		} else {
    		return super.isPermitted(principals, permission);
		}
	}


    /**
     * 清空当前用户权限信息
     */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 指定principalCollection 清除
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
