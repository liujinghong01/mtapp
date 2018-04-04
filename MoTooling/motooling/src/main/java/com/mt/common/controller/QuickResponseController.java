package com.mt.common.controller;

import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.imagesUtil.ZXingUtils;
import com.mt.common.utils.qiniuyun.QiniuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-31   13:51
 * @Description: 生成二维码 控制器 类
 * @Modified By:
 */
@Controller
@Api(value = "生成二维码", description = "生成二维码")
public class QuickResponseController extends BaseController {
    public static final String imagePath = ResourceBundle.getBundle("config/system").getString("linux.image.path");

    @Autowired
    ServletContext servletContext;

    @Autowired
    HttpServletRequest httpServletRequest;

    @ApiOperation(value = "生成二维码", notes = "生成二维码")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/upload/quick/response")
    public Object selectRoleResource(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String context = param.get("context").toString();
            InputStream is = ZXingUtils.getQRCodeInputSterm(context, 0, 0);

            String bucketName = "";
            switch (param.get("project").toString()) {
                case QiniuUtil.BUCKET_NAME_MOTOOLING:
                    bucketName = QiniuUtil.BUCKET_NAME_MOTOOLING;
                    break;
                case QiniuUtil.BUCKET_NAME_MOWORK:
                    bucketName = QiniuUtil.BUCKET_NAME_MOWORK;
                    break;
            }
            String url = QiniuUtil.uploadFile(is, bucketName, param.get("company_id").toString(), param.get("dep_id").toString());
            Map<String, Object> mapReturn = new HashMap<>();
            mapReturn.put("url", url);
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}
