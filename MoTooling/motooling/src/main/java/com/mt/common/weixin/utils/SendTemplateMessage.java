package com.mt.common.weixin.utils;

import com.mt.common.core.config.Resources;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.RedisUtil;
import com.mt.common.weixin.WeixinUtil;
import com.mt.common.weixin.model.AccessToken;
import com.mt.common.weixin.model.Template;
import com.mt.common.weixin.model.TemplateParam;
import com.mt.upms.common.model.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SendTemplateMessage {

    private final static Logger Logger = LoggerFactory.getLogger(SendTemplateMessage.class);

    public static boolean sendTemplateMsg(String token, Template template) {
        boolean flag = false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
        System.out.println(template.toJSON());
        JSONObject jsonResult = WeixinUtil.httpRequest(requestUrl, "POST", template.toJSON());
        if (jsonResult != null) {
            int errorCode = jsonResult.getInt("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            if (errorCode == 0) {
                flag = true;
            } else {
                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
                flag = false;
            }
        }
        return flag;
    }

    public static boolean send(User user, String ip) {
        Template tem = new Template();
        tem.setTemplateId(Resources.WX_TYPE.getString("template_one"));
        tem.setTopColor("#00DD00");
        tem.setToUser(user.getOpenId());
        tem.setUrl("");
        List<TemplateParam> paras = new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", user.getFullname() + " 您好，您的账号已登陆", "#FF3333"));
        paras.add(new TemplateParam("time", DateUtil.getDateTime(), "#0044BB"));
        paras.add(new TemplateParam("ip", ip, "#0044BB"));
        paras.add(new TemplateParam("reason", "如果本次登陆不是您本人所为，请点击本条信息，立即锁定账号。", "#AAAAAA"));
        tem.setTemplateParamList(paras);
        String tokenIsOverdue = WeixinUtil.getAccessTokenIsOverdue();
        boolean result = sendTemplateMsg(tokenIsOverdue, tem);
        //如果失败 重新获取 token 再发一次
        Logger.info("发送微信推送消息获取token： {}   当前token为： {}",result,tokenIsOverdue);
        if (!result) {
            AccessToken accessToken = WeixinUtil.getAccessToken(Resources.WX_TYPE.getString("mt_wx_appid"), Resources.WX_TYPE.getString("mt_wx_secret"));
            RedisUtil.setString("AccessToken_appid", accessToken.getExpiresIn() - 5, accessToken.getToken());
            sendTemplateMsg(tokenIsOverdue, tem);
        }
        return result;
    }

    public static void main(String[] args) {
        Template tem=new Template();
        tem.setTemplateId( Resources.WX_TYPE.getString("template_one"));
        tem.setTopColor("#00DD00");
        tem.setToUser("oRNMz1Nac13zkjEbfa7GgpdvOKgY");
        tem.setUrl("");

        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first","您好，您的账号已登陆","#FF3333"));
        paras.add(new TemplateParam("time", DateUtil.getDateTime(),"#0044BB"));
        paras.add(new TemplateParam("ip","1.1.1.1","#0044BB"));
        paras.add(new TemplateParam("reason","如果本次登陆不是您本人所为，请点击本条信息，立即锁定账号。","#AAAAAA"));
        tem.setTemplateParamList(paras);
        String tokenIsOverdue = WeixinUtil.getAccessTokenIsOverdue();
        boolean result=sendTemplateMsg(tokenIsOverdue,tem);

//        String appid = Resources.WX_TYPE.getString("mt_wx_appid");
//        String secret = Resources.WX_TYPE.getString("mt_wx_secret");
//        AccessToken accessToken = WeixinUtil.getAccessToken(appid, secret);


        //oRNMz1Nac13zkjEbfa7GgpdvOKgY
        //FK_N7kbNBIOiqB0_WHu6O5mA0I4R6EhN2WLLwBn9_ZT0bxwWByM4zjrcVlKsL1vVJmUq822FoBiMs1UihjmDwMswpz1rdcIbbWd2quiNAGm3_yTkwuAiCTuhGByVbndOTJWjAAAFVI
//        List<String> openIdList = WeixinUtil.getopenIdList();
//        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
//        String accessTokenIsOverdue = WeixinUtil.getAccessTokenIsOverdue();
//        url = url.replace("ACCESS_TOKEN", accessTokenIsOverdue);
//        System.out.println(accessTokenIsOverdue);
//        url = url.replace("OPENID", "oRNMz1Nac13zkjEbfa7GgpdvOKgY");
//        JSONObject get = WeixinUtil.httpRequest(url, "GET", null);
//        Map<String,Object> map = get;+
//        System.out.println(get.toString());
//        System.out.println(JSONObject.fromObject(map));
//        String mapS = "{\"subscribe\":1,\"openid\":\"oRNMz1Nac13zkjEbfa7GgpdvOKgY\",\"nickname\":\"Alnwick\",\"sex\":1,\"language\":\"zh_CN\"," +
//                "\"city\":\"长沙\",\"province\":\"湖南\",\"country\":\"中国\"," +
//                "\"headimgurl\":\"http://wx.qlogo.cn/mmopen/hxks0oic8Tt7ExB03y8Rhh48ziaFeK0TVCiaoibUz1rvG7hUBmnO5qLA2DSO2E6WjQ7Qj6Py8t72wa3W3H6k5SibwEFVBH5Y7o0so/0\",\"subscribe_time\":1510042571," +
//                "\"unionid\":\"oP4Xow4pHLQJG6gVwXIcU2uUikZY\",\"remark\":\"\",\"groupid\":0,\"tagid_list\":[]}";
//        Map<String,Object> map = JSONObject.fromObject(mapS);
//        System.out.println(map);

    }
}
