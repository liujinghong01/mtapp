package com.mt.common.controller;


import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 支付宝回调类
 * <p>
 * Alnwick
 */

@Controller
@RequestMapping
@Api(value = "回调接口", description = "回调接口")
public class AlipayNotifyController extends BaseController {

    private final static Logger log = Logger.getLogger(AlipayNotifyController.class);

    @Autowired
    private UserService userService;


    @PostMapping(value = "/alipay/notify")
    @ApiOperation(value = "支付宝回调接口处理", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    public void buyGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            log.debug("支付宝回调参数-----------" + name + "=" + valueStr);
        }

        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        log.info("支付宝回调返回------------------" + "" + out_trade_no + "," + trade_no + "," + trade_status);


    }

}
