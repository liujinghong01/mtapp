package com.mt.upms.user.controller.pc;


import com.mt.common.controller.BaseController;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.LoggerUtils;
import com.mt.common.utils.MathUtil;
import com.mt.common.utils.VerifyCodeUtils;
import com.mt.upms.common.model.User;
import com.mt.upms.user.bo.Login;
import com.mt.upms.user.manager.UserManager;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 
 * 用户登录相关，不需要做登录限制
 *
 */
@Api(value = "登录接口", description = "登录接口")
@RestController
@Scope(value="prototype")
public class UserLoginController extends BaseController {

	@Resource
    UserService userService;

	/**
	 * 注册 && 登录
	 * @param vcode		验证码	
	 * @param entity	UUser实体
	 * @return
	 */
	@ApiOperation(value="注册登录")
	@PostMapping("/register")
	public Object subRegister(@ApiParam(required = true,value = "用户注册信息") User entity,String vcode, ModelMap modelMap){
		if(!VerifyCodeUtils.verifyCode(vcode)){
			modelMap.addAttribute("msg","验证码不正确！");
			return setModelMap(modelMap, HttpCode.BAD_VCODE);
		}
		String email =  entity.getEmail();
		User user = userService.findUserByEmail(email);
		if(null != user){
			modelMap.addAttribute("msg","帐号|Email已经存在！");
			return setModelMap(modelMap, HttpCode.BAD_EMAIL);
		}
		Date date = new Date();
		entity.setCreatedAt(date);
		//把密码md5
		entity = UserManager.md5Pswd(entity);
		entity = userService.insert(entity);
		LoggerUtils.fmtDebug(getClass(), "注册插入完毕！", JSONObject.fromObject(entity).toString());
		entity = TokenManager.login(entity, Boolean.TRUE);
		Session session = TokenManager.getSession();
		Map<String, Object> sessionMap = new HashMap<>();
		sessionMap.put("token_id", session.getId().toString());
		LoggerUtils.fmtDebug(getClass(), "注册后，登录完毕！", JSONObject.fromObject(entity).toString());
		modelMap.addAttribute("msg","注册成功！");
		return setModelMap(modelMap, HttpCode.OK);
	}
	/**
	 * 登录提交
	 * @param entity		登录的UUser
	 * @param rememberMe	是否记住
	 * @param request		request，用来取登录之前Url地址，用来登录后跳转到没有登录之前的页面。
	 * @return
	 */
	@ApiOperation(value="用户登录")
	@PostMapping("/login")
	public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody Login login, ModelMap modelMap, HttpServletRequest request){
		login.getUser().setPassword((MathUtil.getMD5(login.getUser().getPassword())));
		User user = TokenManager.login(login.getUser(), login.getRememberMe());
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (user!=null) {
			request.setAttribute("msg", "[" + user.getUsername() + "]登录成功.");
			Map<String, Object> sessionMap = new HashMap<>();
			sessionMap.put("token_id", session.getId().toString());
			return setSuccessModelMap(modelMap, sessionMap);
		}
		modelMap.addAttribute("msg","您的账号或密码不正确！");
		return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		try {
			TokenManager.logout();
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			logger.error("errorMessage:" + e.getMessage());
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
		}
		return resultMap;
	}





}
