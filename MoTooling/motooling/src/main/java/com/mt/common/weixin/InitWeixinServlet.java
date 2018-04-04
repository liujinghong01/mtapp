package com.mt.common.weixin;

import com.mt.common.core.config.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by lrd on 2017/9/4.
 */
public class InitWeixinServlet extends HttpServlet {

    private static final long serivalVersionUID= 1L;
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    /**
     * Constructor of the object.
     */
    public InitWeixinServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        //微信
        TokenThread.mt_wx_appid = Resources.WX_TYPE.getString("mt_wx_appid");
        TokenThread.mt_wx_appsecret = Resources.WX_TYPE.getString("mt_wx_secret");
        //小程序
        /*TokenThread.mt_wp_appid = Resources.WX_TYPE.getString("mt_wp_appid");
        TokenThread.mt_wp_appsecret = Resources.WX_TYPE.getString("mt_wp_secret");*/
        log.info("weixin api appid:{}",TokenThread.mt_wx_appid);
        log.info("weixin api appsecret:{}",TokenThread.mt_wx_appsecret);
        //未配置appid、appsecret时给出提示
        if("".equals(TokenThread.mt_wx_appid )|| "".equals(TokenThread.mt_wx_appsecret)){
            log.error("appid and appsecret configuration error,please check carefully.");
        }else{
            //启动定时获取access_token的线程
            new Thread(new TokenThread()).start();
        }
    }
}
