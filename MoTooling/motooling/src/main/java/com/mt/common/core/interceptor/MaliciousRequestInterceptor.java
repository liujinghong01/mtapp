package com.mt.common.core.interceptor;



import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 恶意请求拦截器
 *
 */
public class MaliciousRequestInterceptor extends BaseInterceptor {
	private static Logger log = LoggerFactory.getLogger(MaliciousRequestInterceptor.class);
	/**
	 * 拦截所有请求,否则拦截相同请求
	 */
	private Long minRequestIntervalTime;
	/**
	 * 允许的最小请求间隔
	 */
	private Boolean allRequest = false;
	/**
	 * 允许的最大恶意请求次数
	 */
	private Integer maxMaliciousTimes;

	JSONObject jsonObject = new JSONObject();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with,Access-Control-Allow-Origin,EX-SysAuthToken,EX-JSESSIONID,CONTENT-TYPE");

		return super.preHandle(request, response, handler);
	}

	public void setAllRequest(Boolean allRequest) {
		this.allRequest = allRequest;
	}

	public void setMinRequestIntervalTime(Long minRequestIntervalTime) {
		this.minRequestIntervalTime = minRequestIntervalTime;
	}

	public void setMaxMaliciousTimes(Integer maxMaliciousTimes) {
		this.maxMaliciousTimes = maxMaliciousTimes;
	}
}
