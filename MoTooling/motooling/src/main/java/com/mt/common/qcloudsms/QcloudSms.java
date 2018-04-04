package com.mt.common.qcloudsms;

import com.mt.common.bo.AccountValidatorUtil;
import com.qcloud.sms.SmsSingleSender;
import com.qcloud.sms.SmsSingleSenderResult;

import java.util.ArrayList;

/**
* @Author: Fhk
* @Description:
* @create    2017/11/25 13:48
* @Modified By:
*/
public class QcloudSms {

    /**
     * 腾讯云发送短信
     * @param nationCode
     * @param phoneNum
     * @param params
     * @return
     * 0成功，其它失败
     */
    public static int sendSms(String nationCode, String phoneNum, ArrayList<String> params, StringBuilder err) {
        try {
            //请根据实际 appid 和 appkey 进行开发，以下只作为演示 sdk 使用
            //appid,appkey,templId申请方式可参考接入指南 https://www.qcloud.com/document/product/382/3785#5-.E7.9F.AD.E4.BF.A1.E5.86.85.E5.AE.B9.E9.85.8D.E7.BD.AE

            //初始化单发
            SmsSingleSender singleSender = new SmsSingleSender(AccountValidatorUtil.QCLOUD_SMS_APPID, AccountValidatorUtil.QCLOUD_SMS_APPKEY);
            SmsSingleSenderResult singleSenderResult;

            //指定模板单发
            //假设短信模板 id 为 1，模板内容为：测试短信，{1}，{2}，{3}，上学。
            singleSenderResult = singleSender.sendWithParam(nationCode, phoneNum,
                    AccountValidatorUtil.QCLOUD_SMS_TMPLID, params, "", "", "");
            System.out.println(singleSenderResult);
            err.append( singleSenderResult.errMsg);
            return singleSenderResult.result;
        } catch (Exception e) {
            e.printStackTrace();
            return  -1;
        }
    }
}
