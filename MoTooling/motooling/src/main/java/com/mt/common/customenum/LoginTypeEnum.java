package com.mt.common.customenum;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-26   10:08
 * @Description:
 * @Modified By:
 */
public enum LoginTypeEnum {

    BROWSER(1, "浏览器"),
    APP(2, "移动app"),
    WECHAT_WEB(3, "微信网页"),
    WECHAT_SMALL_PROGRAM(4, "微信小程序"),
    WECHAT_PUBLIC_NUMBER(5, "微信公众号");

   private int code;
   private String name;

    LoginTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(int code){
        LoginTypeEnum[] values = LoginTypeEnum.values();
        for (LoginTypeEnum value: values) {
            if (value.code==code) {
                return value.name;
            }
        }
        return null;
    }


}
