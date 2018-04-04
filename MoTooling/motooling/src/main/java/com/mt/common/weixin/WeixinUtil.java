package com.mt.common.weixin;

import com.mt.common.core.config.Resources;
import com.mt.common.utils.RedisUtil;
import com.mt.common.weixin.model.AccessToken;
import com.mt.common.weixin.model.WeiXinUserInfo;
import com.mt.common.weixin.model.WeixinOauth2Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lrd on 2017/9/4.
 */
public class WeixinUtil {
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（get、post）
     * @param outputstr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    @SuppressWarnings("deprecation")
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputstr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            //从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            //设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            //当有数据提交时
            if (null != outputstr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                //注意编码格式，防止中文乱码
                outputStream.write(outputstr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            //释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException e) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /**
     * 获取accessToekn
     *
     * @param appid     凭证
     * @param appsecret 密匙
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 把 token 存入redis
     *
     * @return
     */
    public static String getAccessTokenIsOverdue() {
        AccessToken accessToken = null;
        String token = RedisUtil.getString("AccessToken_appid");
        if (token != null) {
            return token;
        } else {
            accessToken = getAccessToken(Resources.WX_TYPE.getString("mt_wx_appid"), Resources.WX_TYPE.getString("mt_wx_secret"));
            token = accessToken.getToken();
            RedisUtil.setString("AccessToken_appid", accessToken.getExpiresIn() - 5, token);
        }
        return token;
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


    //获取JSAPI_Ticket
    public static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * 获取jsapi_ticket
     *
     * @param accessToken
     * @return
     */
    public static String JSApiTIcket(String accessToken) {
        int result = 0;
        String jsApiTicket = null;
        //拼装创建菜单Url
        String url = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
        //调用接口获取jsapi_ticket
        JSONObject jsonObject = httpRequest(url, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                jsApiTicket = jsonObject.getString("ticket");
            } catch (JSONException e) {
                if (0 != jsonObject.getInt("errcode")) {
                    result = jsonObject.getInt("errcode");
                    log.error("JSAPI_Ticket获取失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                }
            }
        }
        return jsApiTicket;
    }

    /**
     * sha1加密
     *
     * @param str
     * @return
     */
    public static String sha1Encrypt(String str) {
        String signature = null;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return signature;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    /**
     * 获取网页授权凭证
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        log.info("appId = " + appId + "        appSecret = " + appSecret + "     code = " + code);
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
                wat.setUnionid(jsonObject.getString("unionid"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */

    public static WeiXinUserInfo getUserInfo(String accessToken, String openId) {
        WeiXinUserInfo weiXinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weiXinUserInfo = new WeiXinUserInfo();
                // 用户的标识
                weiXinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                weiXinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weiXinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weiXinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weiXinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weiXinUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                weiXinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                //unionid
                weiXinUserInfo.setUnionid(jsonObject.getString("unionid"));
                //特权
                weiXinUserInfo.setPrivilege(jsonObject.getString("privilege"));
                //语言
                weiXinUserInfo.setPrivilege(jsonObject.getString("language"));

            } catch (Exception e) {
                weiXinUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return weiXinUserInfo;
    }

    /*
        urlDecodeUTF8 编码
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
        urlDecodeUTF8 解码
     */
    public static String urlDecodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLDecoder.decode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过code 获取 token  调取微信用户的信息
     *
     * @param code
     * @return
     */
    public static WeiXinUserInfo  getWeixinOauth2Token(String code, String state) {
        // 用户同意授权后，能获取到code
        WeiXinUserInfo weiXinUserInfo = null;
        // 用户同意授权
        String appid = null;
        String secret = null;

        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            // pc
            if (state.startsWith("mt_pc")) {
                appid = Resources.WX_TYPE.getString("mt_wx_pc_appid");
                secret = Resources.WX_TYPE.getString("mt_wx_pc_secret");
                // 公众号
            } else {
                appid = Resources.WX_TYPE.getString("mt_wx_appid");
                secret = Resources.WX_TYPE.getString("mt_wx_secret");
            }
            log.info("appid = " + appid + "        secret = " + secret);
            WeixinOauth2Token weixinOauth2Token = WeixinUtil.getOauth2AccessToken(appid, secret, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 用户授权作用域
            String scope = weixinOauth2Token.getScope();
            // 获取用户信息
            weiXinUserInfo = WeixinUtil.getUserInfo(accessToken, openId);
            // 设置要传递的参数
        }
        return weiXinUserInfo;
    }

    /**
     * 获取或有订阅公众号关注的openId
     *
     * @return openId list 集合
     */


    public static List<String> getopenIdList() {
        String getopenIdListUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
        String accessTokenIsOverdue = WeixinUtil.getAccessTokenIsOverdue();
        log.info("获取到的 token {}", accessTokenIsOverdue);
        getopenIdListUrl = getopenIdListUrl.replace("ACCESS_TOKEN", accessTokenIsOverdue);
        JSONObject get = WeixinUtil.httpRequest(getopenIdListUrl, "GET", null);
        Map<String, Object> data = (Map<String, Object>) get.get("data");
        log.info("获取用户的信息 ： {}", data);
        return (List<String>) data.get("openid");
    }

    /**
     * 根据 token 和 openid 获取用户信息
     *
     * @return 用户信息Map
     */


    public static Map<String, Object> oneWeiXinUser(String openId) {
        String oneWeiXinUserUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
        String tokenIsOverdue = getAccessTokenIsOverdue();
        oneWeiXinUserUrl = oneWeiXinUserUrl.replace("ACCESS_TOKEN", tokenIsOverdue);
        oneWeiXinUserUrl = oneWeiXinUserUrl.replace("OPENID", openId);
        JSONObject getUser = WeixinUtil.httpRequest(oneWeiXinUserUrl, "GET", null);
        log.info("获取用户的信息 ： {}", getUser);
        return getUser;
    }

    /**
     * 微信用户 授权获取  Code 方法
     *
     * @param url
     * @return
     */
    static String requestUrl = Resources.WX_TYPE.getString("mt_wx_app_authorization_url");

    /**
     * APP端 授权登陆
     *
     * @param url
     * @return
     */
    public static String redirectWeiXin(String url) {
        requestUrl = requestUrl.replace("APPID", Resources.WX_TYPE.getString("mt_wx_appid")).
                replace("REDIRECT_URL", Resources.WX_TYPE.getString("mt_wx_redirect_url").replace("STATE", "getCode"));
        return requestUrl;
    }

    public static void main(String[] args) {
        String code = "071GSV061ctQ9N1RP1361ssV061GSV0j";
        String state = "STATE";
        Map<String, Object> map = new HashMap<>();
        try {
            // 用户同意授权
            if (!"authdeny".equals(code)) {
                // 获取网页授权access_token
                String appid = Resources.WX_TYPE.getString("mt_wx_appid");
                String secret = Resources.WX_TYPE.getString("mt_wx_secret");
                WeixinOauth2Token weixinOauth2Token = WeixinUtil.getOauth2AccessToken(appid, secret, code);
                // 网页授权接口访问凭证
                String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                String openId = weixinOauth2Token.getOpenId();
                // 用户授权作用域
                String scope = weixinOauth2Token.getScope();
                // 获取用户信息
                WeiXinUserInfo weiXinUserInfo = WeixinUtil.getUserInfo(accessToken, openId);
                System.out.println(scope + "         " + openId + "       " + accessToken + "   " + JSONObject.fromObject(weiXinUserInfo));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }


}
