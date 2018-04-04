package com.mt.common.core.shiro.filter;

import com.mt.common.utils.LoggerUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 权限校验 Filter
 * 
 */
public class PermissionFilter extends AccessControlFilter {



	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		//先判断带参数的权限判断
		Subject subject = getSubject(request, response);
		if(null != mappedValue){
			String[] arra = (String[])mappedValue;
			for (String permission : arra) {
				if(subject.isPermitted(permission)){
					return Boolean.TRUE;
				}
			}
		}
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		String uri = httpRequest.getRequestURI();//获取URI
		String basePath = httpRequest.getContextPath();//获取basePath
		if(null != uri && uri.startsWith(basePath)){
			uri = uri.replaceFirst(basePath, "");
		}
		if(subject.isPermitted(uri)){
			return Boolean.TRUE;
		}
		Map<String,String> resultMap = new HashMap<String, String>();
		LoggerUtils.debug(getClass(), "当前用户没有权限");
		resultMap.put("login_status", "300");
		resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");
		ShiroFilterUtils.out(response, resultMap);
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
			Subject subject = getSubject(request, response);  
	        if (null == subject.getPrincipal()) {//表示没有登录
	            saveRequest(request);
				LoggerUtils.debug(getClass(), "当前用户没有权限");
				Map<String,String> resultMap = new HashMap<String, String>();
				resultMap.put("login_status", "300");
				resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");
				ShiroFilterUtils.out(response, resultMap);
	        } else {  
	            //返回401未授权状态码
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        }  
		return Boolean.FALSE;
	}

}
