/**
 * 
 */
package com.mt.common.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.mt.common.core.exception.BaseException;
import com.mt.common.core.exception.IllegalParameterException;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.core.support.DateFormat;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.Constants;
import com.mt.common.utils.InstanceUtil;
import com.mt.common.utils.WebUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 控制器基类
 * 包装接口相应的基本数据
 *
 */
public abstract class BaseController {

	protected int pageNo =1;
	public static  int pageSize = 10;
	protected Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
	public static String URL404 =  "/404.html";

	private final static String PARAM_PAGE_NO = "pageNo";

	protected String pageSizeName = "pageSize";
	protected final Logger logger = LogManager.getLogger(this.getClass());

	/** 获取当前用户Id */
	protected Long getCurrUser() {
		return WebUtil.getCurrentUser();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new DateFormat(), true));
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
		return setSuccessModelMap(modelMap, null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
		return setModelMap(modelMap, HttpCode.OK, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
		return setModelMap(modelMap, code, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
		Map<String, Object> map = InstanceUtil.newLinkedHashMap();
		map.putAll(modelMap);
		modelMap.clear();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (!key.startsWith("org.springframework.validation.BindingResult") && !"void".equals(key)) {
				modelMap.put(key, map.get(key));
			}
		}
		if (data != null) {
			if (data instanceof Page) {
				Page<?> page = (Page<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
				modelMap.put("iTotalRecords", page.getTotal());
				modelMap.put("iTotalDisplayRecords", page.getTotal());
			} else if (data instanceof List<?>) {
				modelMap.put("data", data);
				modelMap.put("iTotalRecords", ((List<?>) data).size());
				modelMap.put("iTotalDisplayRecords", ((List<?>) data).size());
			} else {
				modelMap.put("data", data);
			}
		}
		modelMap.put("reasonCode", code.value());
		return ResponseEntity.ok(modelMap);
	}

	/** 异常处理 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		logger.error(Constants.Exception_Head, ex);
		ModelMap modelMap = new ModelMap();
		if (ex instanceof BaseException) {
			((BaseException) ex).handler(modelMap);
		} else if (ex instanceof IllegalArgumentException) {
			new IllegalParameterException(ex.getMessage()).handler(modelMap);
		} else if (ex instanceof UnauthorizedException) {
			modelMap.put("reasonCode", HttpCode.FORBIDDEN.value());
			modelMap.put("description","权限不足，如需要操作、请您联系管理人员");
		} else {
			modelMap.put("reasonCode", HttpCode.INTERNAL_SERVER_ERROR.value());
			String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
			modelMap.put("description", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
		}
		response.setContentType("application/json;charset=UTF-8");
//		modelMap.put("timestamp", System.currentTimeMillis());
		logger.info(JSON.toJSON(modelMap));
		byte[] bytes = JSON.toJSONBytes(modelMap, SerializerFeature.DisableCircularReferenceDetect);
		response.getOutputStream().write(bytes);
	}

	/**
	 * 往Request里带值
	 * @param request
	 * @param key
	 * @param value
	 */
	protected static void setValue2Request(HttpServletRequest request,String key,Object value){
		request.setAttribute(key, value);
	}

	/**
	 * [获取session]
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request){
		return request.getSession();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		BaseController.pageSize = pageSize;
	}

	public ModelAndView redirect(String redirectUrl, Map<String,Object>...parament){
		ModelAndView view = new ModelAndView(new RedirectView(redirectUrl));
		if(null != parament && parament.length > 0){
			view.addAllObjects(parament[0]);
		}
		return view;
	}
	public ModelAndView redirect404(){
		return new ModelAndView(new RedirectView(URL404));
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> prepareParams(Object obj, HttpServletRequest request) throws Exception {
		if (request != null) {
			String pageNoStr   = (String)request.getParameter(PARAM_PAGE_NO),
					pageSizeStr = (String)request.getParameter(pageSizeName);
			if (com.mt.common.utils.StringUtils.isNotBlank(pageNoStr)) {
				pageNo = Integer.parseInt(pageNoStr);
			}
			if (com.mt.common.utils.StringUtils.isNotBlank(pageSizeStr)) {
				pageSize = Integer.parseInt(pageSizeStr);
			}
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params = BeanUtils.describe(obj);
		params = handleParams(params);
		// 回填值项
		//BeanUtils.populate(obj, params);
		return params;
	}
	private Map<String, Object> handleParams(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != params) {
			Set<Map.Entry<String, Object>> entrySet = params.entrySet();

			for (Iterator<Map.Entry<String, Object>> it = entrySet.iterator(); it.hasNext(); ) {
				Map.Entry<String, Object> entry = it.next();
				if (entry.getValue() != null) {
					result.put(entry.getKey(), com.mt.common.utils.StringUtils.trimToEmpty((String)entry.getValue()));
				}
			}
		}
		return result;
	}

	protected Boolean isLoginUser(String tokenId) {
		if(TokenManager.customSessionManager.getSession(tokenId) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将Map的key从下划线转成驼峰
	 * @param map
	 * @return
	 */
	protected Map underlineToCamel(Map<String, Object> map) {
		if (map == null || map.isEmpty() || map.size() < 1) { return null; }
		Map<String, Object>retMap = new HashMap<>();
		for (String key: map.keySet()) {
			retMap.put(com.mt.common.utils.StringUtils.underlineToCamel(key), map.get(key));
		}
		return retMap;
	}

	/**
	 * 将Map的key从驼峰转为下划线
	 * @param map
	 * @return
	 */
	protected Map camelToUnderline(Map<String, Object> map) {
		if (map == null || map.isEmpty() || map.size() < 1) { return null; }
		Map<String, Object>retMap = new HashMap<>();
		for (String key: map.keySet()) {
			retMap.put(com.mt.common.utils.StringUtils.camelToUnderline(key), map.get(key));
		}
		return retMap;
	}


	/**
	 * 将mapList的key从下划线转成驼峰
	 * @param mapList
	 * @return
	 */
	protected List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.isEmpty() || mapList.size() < 1) { return null; }
		List<Map<String, Object>>  retList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map: mapList) {
			Map<String, Object> retMap = new HashMap<>();
			for (String key : map.keySet()) {
				retMap.put(com.mt.common.utils.StringUtils.underlineToCamel(key), map.get(key));
			}
			retList.add(retMap);
		}
		return retList;
	}


	/**
	 * 将mapList的key从驼峰转为下划线
	 * @param mapList
	 * @return
	 */
	protected List<Map<String, Object>> camelToUnderline(List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.isEmpty() || mapList.size() < 1) { return null; }
		List<Map<String, Object>>  retList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map: mapList) {
			Map<String, Object> retMap = new HashMap<>();
			for (String key : map.keySet()) {
				retMap.put(com.mt.common.utils.StringUtils.camelToUnderline(key), map.get(key));
			}
			retList.add(retMap);
		}
		return retList;
	}

	/**
	 * 生成页信息
	 * @param totalCount
	 * @param pageSize
	 * @param currPage
	 * @return
	 */
	protected Map<String,Object> createPageMap(Long totalCount, Integer pageSize, Integer currPage) {
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("curr_page", currPage);
		pageMap.put("page_size", pageSize);
		pageMap.put("total_count", totalCount);
		pageMap.put("total_page", totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
		return pageMap;
	}

}
