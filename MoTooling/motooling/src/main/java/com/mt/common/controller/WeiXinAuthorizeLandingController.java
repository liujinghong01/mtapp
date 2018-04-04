package com.mt.common.controller;

import com.mt.common.core.config.Resources;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.weixin.WeixinUtil;
import com.mt.common.weixin.model.WeiXinUserInfo;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Alnwick
 * @Date: create in 2018-02-06   9:36
 * @Description:
 * @Modified By:
 */
@RestController
@Api(value = "微信授权登陆", description = "微信授权登陆")
public class WeiXinAuthorizeLandingController extends BaseController{

    private final static Logger log = Logger.getLogger(AlipayNotifyController.class);

    @Autowired
    private UserService userService;

    static String url = "";

    /**
     * 微信 第三方授权登陆获取  code 码
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/wechat/oauth")
    @ApiOperation(value = "微信 授权登陆", notes = "")
    public ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        String redirectUrl = "";
        try {
            url = request.getParameter("callback_uri");
            logger.info(url);
            redirectUrl = WeixinUtil.redirectWeiXin(url);
            logger.info(redirectUrl);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return new ModelAndView(new RedirectView(redirectUrl));
    }

    /**
     * 微信 授权后获取code 进行 下一部操作
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/wechat/callback")
    @ApiOperation(value = "微信 授权后获取code 进行 下一部操作", notes = "公众号")
    public ModelAndView callbackWx(HttpServletRequest request, ModelMap modelMap) throws ServletException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        logger.info(code + "          state" + state);
        WeiXinUserInfo weiXinUserInfo = WeixinUtil.getWeixinOauth2Token(code, state);
        User user = userService.selectByWechat(weiXinUserInfo.getUnionid());
        if (user == null) {
            userService.addUser(weiXinUserInfo);
            user = userService.selectByWechat(weiXinUserInfo.getUnionid());
            log.info(user.getOpenId());
        }
        if (user.getPassword() == null) {
            user.setPassword("");
        }
        User tokenLogin = TokenManager.login(user, false);
        Session session = TokenManager.getSession();
        String token = session.getId().toString();
        Long uid = user.getUid();
        String redirectUrl = "";
        logger.info(url);
        if (state.startsWith("mt_pc")) {
            redirectUrl = Resources.WX_TYPE.getString("mt_wx_topath_pc_url") + "token=" + token + "&uid=" + uid;
        } else {
            if(url.contains("&token=")){
                url = url.substring(0,url.lastIndexOf("&token="));
            }
            redirectUrl = Resources.WX_TYPE.getString("mt_wx_topath_gzh_url") + url + "&token=" + token + "&uid=" + uid;
        }
        logger.info(redirectUrl);
        return new ModelAndView(new RedirectView(redirectUrl));
    }





}
