package com.mt.common.weixin;

import com.mt.common.weixin.model.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lrd on 2017/9/4.
 */
public class TokenThread implements Runnable  {
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);

    /**
     * 保存微信公众号和小程序appid调用的数据
     */

    //微信公众号唯一凭证
    public static String mt_wx_appid = "";
    //微信公众号唯一凭证密匙
    public static String mt_wx_appsecret = "";

    //微信小程序唯一凭证
    public static String mt_wp_appid = "";
    //微信小程序唯一凭证密匙
    public static String mt_wp_appsecret = "";

    public static AccessToken mt_wx_accessToken = null;
    public static String mt_wx_jsapi_ticket=null;

    public static AccessToken mt_wp_accessToken = null;
    public static String mt_wp_jsapi_ticket=null;


    public void run() {
        // TODO Auto-generated method stub
        while(true){
            try{
                //微信公众号获取accessToken
                mt_wx_accessToken = WeixinUtil.getAccessToken(mt_wx_appid, mt_wx_appsecret);
                //微信公众号获取JSAPI_Ticket
                mt_wx_jsapi_ticket = WeixinUtil.JSApiTIcket(mt_wx_accessToken.getToken());

               /* //微信小程序获取accessToken
                mt_wp_accessToken = WeixinUtil.getAccessToken(mt_wp_appid, mt_wp_appsecret);
                //微信小程序获取JSAPI_Ticket
                mt_wp_jsapi_ticket = WeixinUtil.JSApiTIcket(mt_wp_accessToken.getToken());
                */
                if(null != mt_wx_accessToken){
                    log.info("获取access_token成功，有效时长{}秒 token:{}",mt_wx_accessToken.getExpiresIn(),mt_wx_accessToken.getToken());
                    log.info("获取jsapi_ticket成功， jsapi_ticket:{}",mt_wx_jsapi_ticket);
                    //休眠700秒
                    Thread.sleep((mt_wx_accessToken.getExpiresIn()-200)*1000);
                }
                else{
                    //如果access_token为null，60秒后在获取
                    Thread.sleep(60*1000);
                }
            }catch(InterruptedException e){
                try{
                    Thread.sleep(60*1000);
                }catch(InterruptedException e1){
                    log.error("{}",e1);
                }
                log.error("{}",e);
            }
        }
    }
    public static void main(String[] args){
        System.out.println(TokenThread.mt_wx_accessToken.getToken());
    }
}
