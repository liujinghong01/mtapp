package com.mt.common.controller;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.mt.common.core.support.pay.AliPay;
import com.mt.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/buy")
@Api(value = "支付接口", description = "支付接口")
public class BuyController extends BaseController{

    private final static Logger log = Logger.getLogger(BuyController.class);

    @ApiOperation(value = "支付宝,微信支付", notes ="")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/submitOrderPay")
    public Object submitOraerPay(@RequestBody Map<String,Object> param,ModelMap modelMap) throws Exception{
        try {
            //生成订单

            //  1、支付宝支付 ，2、微信支付
            Integer payType= param.get("pay_type")==null? null:Integer.valueOf(param.get("pay_type").toString());
            if(payType == 1){
                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                model.setPassbackParams(URLEncoder.encode(param.toString(),"utf-8"));  //描述信息  添加附加数据
                model.setSubject("motooling");//商品标题
                model.setOutTradeNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+(int)(Math.random()*90000+10000)); //商家订单编号
                model.setTimeoutExpress("30m"); //超时关闭该订单时间
                model.setTotalAmount((String) param.get("amout"));  //订单总金额
                model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
                AlipayTradeAppPayResponse response = AliPay.appPay(model, "http://www.motooling.com:8080/alipay/notify");
                log.info(response.toString());
                return response.getBody();

            }else if (payType == 2){
                StringUtils.isBlank(param.get("openid"));
            }

        }catch (Exception ex){

        }





        return null;
    }



}
