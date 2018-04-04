package com.mt.common.core.shiro.filter;

import com.mt.common.core.shiro.session.ShiroSessionRepository;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.LoggerUtils;
import com.mt.upms.common.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 判断登录
 */
public class LoginFilter extends AccessControlFilter {
    final static Class<LoginFilter> CLASS = LoginFilter.class;

    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
    //session获取
    static ShiroSessionRepository shiroSessionRepository;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Access-Control-Allow-Origin,EX-SysAuthToken,EX-JSESSIONID,Content-Type,x-access-token");
        // 如果是 OPTIONS 预请求 就放行
        if ("OPTIONS".equals(req.getMethod())) {
            return false;
        }
        String token = req.getHeader("x-access-token");
        if (token == null) {
            return loginFalse(response);
        }
        User user = shiroSessionRepository.getSessionUser(token);
        if (user == null) {
            return loginFalse(response);
        }
        Subject subject = new Subject.Builder().sessionId(token).buildSubject();
        ThreadContext.bind(subject);
        Session session = SecurityUtils.getSubject().getSession();
        if ("1".equals(user.getClientType())) {
            session.setTimeout(1800000);
            session.touch();
            return true;
        }
        session.setTimeout(259200000);
        session.touch();
        return Boolean.TRUE;

    }

    /**
     * 登陆失败处理
     *
     * @param response
     * @return
     */
    private boolean loginFalse(ServletResponse response) {
        Map<String, String> resultMap = new HashMap<String, String>();
        LoggerUtils.debug(getClass(), "please login");
        resultMap.put("reasonCode", HttpCode.UNAUTHORIZED.toString());
        resultMap.put("description", "please login");//当前用户没有登录！
        ShiroFilterUtils.out(response, resultMap);
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        //保存Request和Response 到登录后的链接
        //saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE;
    }

    public static void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        LoginFilter.shiroSessionRepository = shiroSessionRepository;
    }


}
