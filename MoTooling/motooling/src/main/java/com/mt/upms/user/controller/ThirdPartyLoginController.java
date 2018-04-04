package com.mt.upms.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.mt.common.controller.BaseController;
import com.mt.common.core.config.Resources;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.core.support.login.ThirdPartyLoginHelper;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 第三方登录控制类
 *
 */
@Controller
@Api(value = "第三方登录接口", description = "第三方登录接口")
public class ThirdPartyLoginController extends BaseController {

	@Autowired
	UserService userService;

	@PostMapping("/api/sns")
	@ApiOperation(value = "用户登录")
	public void thirdLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String ,Object> param) {
		String url = getRedirectUrl(request, param);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("/sns_success")
	@ApiOperation(value = "登录成功", httpMethod = "GET")
	public String thirdLoginsuccess() {
		return "/sns/success";
	}

	@RequestMapping("/sns_bind")
	@ApiOperation(value = "用户绑定", httpMethod = "GET")
	public String thirdLoginbind() {
		return "/sns/bind";
	}

	@RequestMapping("/sns_fail")
	@ApiOperation(value = "登录失败", httpMethod = "GET")
	public String thirdLoginfail() {
		return "/sns/fail";
	}

	@RequestMapping("/callback/wx")
	@ApiOperation(value = "微信登录回调", httpMethod = "GET")
	public String wxCallback(HttpServletRequest request, ModelMap modelMap) {
		String host = request.getHeader("host");
		String redirectUrl = "";
		try {
			String code = request.getParameter("code");
			if (StringUtils.isNotBlank(code)) {// 如果不为空
				// 获取token和openid
				Map<String, String> map = ThirdPartyLoginHelper.getWxTokenAndOpenid(code, host);
				String openId = map.get("openId");
				if (StringUtils.isNotBlank(openId)) {// 如果openID存在
					// 获取第三方用户信息存放到session中
					User thirdUser = ThirdPartyLoginHelper.getWxUserinfo(map.get("access_token"), openId);
					thirdUser.setProvider("WX");
					redirectUrl = thirdPartyLogin(request, thirdUser);
					// 跳转到登录成功界面
					modelMap.put("retUrl", Resources.THIRDPARTY.getString("third_login_success"));
//					getSession(request).setAttribute("redirectUrl",redirectUrl);
				} else {// 如果未获取到OpenID
					modelMap.put("retUrl", "-1");
				}
			} else {// 如果没有返回令牌，则直接返回到登录页面
				modelMap.put("retUrl", "-1");
			}
		} catch (Exception e) {
			modelMap.put("retUrl", "-1");
			e.printStackTrace();
		}
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject(redirectUrl);
		return "redirect:"+redirectUrl;
	}

	@RequestMapping("/callback/qq")
	@ApiOperation(value = "QQ登录回调", httpMethod = "GET")
	public String qqCallback(HttpServletRequest request, ModelMap modelMap) {
		String host = request.getHeader("host");
		try {
			String code = request.getParameter("code");
			if (StringUtils.isNotBlank(code)) {// 如果不为空
				// 获取token和openid
				Map<String, String> map = ThirdPartyLoginHelper.getQQTokenAndOpenid(code, host);
				String openId = map.get("openId");
				if (StringUtils.isNotBlank(openId)) {// 如果openID存在
					// 获取第三方用户信息存放到session中
					User thirdUser = ThirdPartyLoginHelper.getQQUserinfo(map.get("access_token"), openId);
					thirdUser.setProvider("QQ");
					thirdPartyLogin(request, thirdUser);
					// 跳转到登录成功界面
					modelMap.put("retUrl", Resources.THIRDPARTY.getString("third_login_success"));
				} else {// 如果未获取到OpenID
					modelMap.put("retUrl", "-1");
				}
			} else {// 如果没有返回令牌，则直接返回到登录页面
				modelMap.put("retUrl", "-1");
			}
		} catch (Exception e) {
			modelMap.put("retUrl", "-1");
			e.printStackTrace();
		}

		return "/sns/redirect";
	}

	@RequestMapping("callback/sina")
	@ApiOperation(value = "微博登录回调", httpMethod = "GET")
	public String sinaCallback(HttpServletRequest request, ModelMap modelMap) {
		String host = request.getHeader("host");
		try {
			String code = request.getParameter("code");
			if (StringUtils.isNotBlank(code)) {// 如果不为空
				// 获取token和uid
				JSONObject json = ThirdPartyLoginHelper.getSinaTokenAndUid(code, host);
				String uid = json.getString("uid");
				if (StringUtils.isNotBlank(uid)) {// 如果uid存在
					// 获取第三方用户信息存放到session中
					User thirdUser = ThirdPartyLoginHelper.getSinaUserinfo(json.getString("access_token"),
							uid);
					thirdUser.setProvider("SINA");
					thirdPartyLogin(request, thirdUser);
					// 跳转到登录成功界面
					modelMap.put("retUrl", Resources.THIRDPARTY.getString("third_login_success"));
				} else {// 如果未获取到OpenID
						// 跳转到登录成功界面
					modelMap.put("retUrl", "-1");
				}
			} else {// 如果没有返回令牌，则直接返回到登录页面
					// 跳转到登录成功界面
				modelMap.put("retUrl", "-1");
			}
		} catch (Exception e) {
			// 跳转到登录失败界面
			modelMap.put("retUrl", "-1");
			e.printStackTrace();
		}

		return "/sns/redirect";
	}

	private String thirdPartyLogin(HttpServletRequest request, User user) {
//		User sysUser = null;
		// 查询是否已经绑定过
//		Parameter parameter = new Parameter(getService(), "queryUserIdByThirdParty").setModel(param);
//		Long userId = provider.execute(parameter).getId();
		User sysUser = userService.insertSelective(user);
		logger.info(sysUser);
		if (sysUser.getId() == null) {
//			parameter = new Parameter(getService(), "insertThirdPartyUser").setModel(param);
//			sysUser = (User) provider.execute(parameter).getModel();
			userService.insert(user);
		} else {
//			parameter = new Parameter(getService(), "queryById").setId(param.getId());
//			sysUser = (User) provider.execute(parameter).getModel();
			userService.updateByPrimaryKeySelective(user);
		}
//		LoginHelper.login(request, sysUser.getUsername(), sysUser.getPassword());
//		String redirectUrl = "http://weixin.mowork.cn/#/weixin?toPath=fdsafdas&uid=19&token=fdafdsa";
		User login = TokenManager.login(user, false);
		Session session = TokenManager.getSession();
		String redirectUrl = "http://weixin.mowork.cn/#/weixin?toPath="+request.getParameter("state")+"$uid="+user.getUid()+"&token="+session.getId();

		return redirectUrl;
	}

	private String getRedirectUrl(HttpServletRequest request, Map<String , Object> param) {
		String type = (String)param.get("type");
		String toPath = (String)param.get("toPath");
		String url = "";
		String host = request.getHeader("host");
		url = Resources.THIRDPARTY.getString("authorizeURL_" + type);
		if ("wx".equals(type)) {
			url = url + "?appid=" + Resources.THIRDPARTY.getString("app_id_" + type) + "&redirect_uri=http://" + host
					+ Resources.THIRDPARTY.getString("redirect_url_" + type) + "&response_type=code&scope="
					+ Resources.THIRDPARTY.getString("scope_" + type) + "&state="+toPath;
		} else {
			url = url + "?client_id=" + Resources.THIRDPARTY.getString("app_id_" + type) + "&response_type=code&scope="
					+ Resources.THIRDPARTY.getString("scope_" + type) + "&redirect_uri=http://" + host
					+ Resources.THIRDPARTY.getString("redirect_url_" + type);
		}
		return url;
	}
}
