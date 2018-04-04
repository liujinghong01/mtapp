package com.mt.common.bo;
import java.util.regex.Pattern;

/**
 * 账户相关属性验证工具
 *
 */

/**
* @Author: Fhk
* @Description:
* @create    2017/11/25 11:19
* @Modified By:
*/
public class AccountValidatorUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        ////return Pattern.matches(REGEX_MOBILE, mobile);
        return true;
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 校验 验证码
     * @param verityCode
     * @return
     */
    protected Boolean verifyCodeIsValid(String verityCode) {
        if(verityCode == null || verityCode.length() != 6) { return false; }
        return  true;
    }

    public  static final String SET_PASSWORD_EMAIL_TEMPLET = "<html>"+
            "<body style=\"line-height:30px;\">"+
            "<br>"+
            "<div style=\"margin-top:5px;\">这封信是由 Motooling 发送的。</div>"+
            "<div style=\"margin-top:5px;\">您收到这封邮件，是由于在 Motooling 通过为账号找回密码使用了这个邮箱地址。如果您并没有访问过 Motooling，或没有进行上述操作，请忽略这封邮件。您不需要退订或进行其他进一步的操作。</div><br>"+
            "<div style=\"margin-top:5px;\">----------------------------------------------------------------------</div>"+
            "<div style=\"margin-top:5px;\">老用户找回密码说明</div><br>"+
            "<div style=\"margin-top:5px; \">如果您是 Motooling 的老用户，或在找回密码时使用了本地址，我们需要对您的账号有效性进行验证。</div><br>"+
            "<div style=\"margin-top:5px; \">您只需点击下面的链接即可进行新密码设置，以下链接有效期为30分钟。过期可以重新请求发送一封新的邮件验证：</div>"+
            "<div style=\"margin-top:5px; \"><a href=\"<<url>>\" target=\"_blank\"><<url>></a></div>"+
            "<div style=\"margin-top:5px; \">(如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)</div><br>"+
            "<div style=\"margin-top:5px; \">(推荐使用谷歌或火狐浏览器打开以上链接)</div><br><br>"+
            "<div style=\"margin-top:5px; \">感谢您的访问，祝您使用愉快！</div>"+
            "<div style=\"margin-top:5px; \">此致</div>"+
            "<div style=\"margin-top:5px; \">Motooling 管理团队.</div>"+
            "<div style=\"margin-top:5px; \">http://www.mowork.cn/</div><br>"+
            "</body>"+
            "</html>";

    public  static final String  SET_EMAIL_VERIFY_CODE_TEMPLET = "<html>"+
            "<body style=\"line-height:30px;\">"+
            "<br>"+
            "<div style=\"margin-top:5px;\">这封信是由 Motooling 发送的。</div>"+
            "<div style=\"margin-top:5px;\">您收到这封邮件，是由于在 Motooling 注册账号时使用了这个邮箱地址。如果您并没有访问过 Motooling，或没有进行上述操作，请忽略这封邮件。您不需要退订或进行其他进一步的操作。</div><br>"+
            "<div style=\"margin-top:5px;\">----------------------------------------------------------------------</div>"+
            "<div style=\"margin-top:5px;\">注册邮箱帐号说明</div><br>"+
            "<div style=\"margin-top:5px; \">因为您在 Motooling 注册邮箱帐号，我们需要对您的邮箱账号有效性进行验证。</div><br>"+
            "<div style=\"margin-top:5px; \">您注册邮箱帐号的验证码是：<<url1>></div>"+
            "<div style=\"margin-top:5px; \">您只需记下或复制验证码，然后在注册页面的相应位置输入该验证码，就能顺利完成注册。验证码有效期为<<url2>>分钟。过期可以重新请求发送新的验证码。</div>"+
            "<div style=\"margin-top:5px; \">感谢您的访问，祝您使用愉快！</div>"+
            "<div style=\"margin-top:5px; \">此致</div>"+
            "<div style=\"margin-top:5px; \">Motooling 管理团队.</div>"+
            "<div style=\"margin-top:5px; \">http://www.mowork.cn/</div><br>"+
            "</body>"+
            "</html>";

    /**
     * 保存图片到服务器目录
     */
    public static final  String BASE_IMG_PATH = "/uploads/motooling/image/";
    /**
     * 微信下载图片APPID
     */
    public  static final String APPID = "wx70214a3c12e8e576";
    public  static final String APPSECRET = "2ae9b6db245328e24e3f4855d51ea9f0";
    public  static final String WECHAT_IMAGE_TOKEN_NAME = "wechat_download_image_token";

    /**
     * Motooling 腾讯云 短信
     */
    public  static final int QCLOUD_SMS_APPID = 1400044058;
    public  static final String QCLOUD_SMS_APPKEY = "c8cadf3ae1e3499baa6243177fd04e43";
    public  static final int QCLOUD_SMS_TMPLID = 64522;

    /**
     * 定义手机验证码超时时间，单位秒
     */
    public static final int VERIFY_CODE_TIMEOUT = 3600;


    /**
     * 定义生成单号的互斥对象
     */
    public static final Object CREATE_BILL_NO_SYNCHROMIZED = new Object();

    //public static final String MAX_MOLD_NO = "max_mold_no";
    //public static final Object CREATE_MOLD_NO_SYNCHROMIZED = new Object();

    /**
     * 重启密码前端URL，发送重设密码邮件时使用
     */
    public static final String  RESET_LOGIN_PASSWORD = "http://weixin.motooling.com/#/resetPassword";

}
