package com.mt.upms.user.controller.app;

import com.mt.common.bo.AccountValidatorUtil;
import com.mt.common.bo.Base64Convert;
import com.mt.common.controller.BaseController;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.qiniuyun.QiniuUtil;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @Author: Fhk
* @Description:
* @create    2017.9.20
* @Modified By:
*/
@Api(value = "App端修改个人头像", description = "App端修改个人头像")
@RestController
@Scope(value = "prototype")
public class ModifyAvatarController extends BaseController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    UserService userService;

    @ApiOperation(value = "U011 App端修改个人头像")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"img\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/modify-avatar")
    public Object appModifyAvatar(@ApiParam(required = true, value = "App端修改个人头像") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String pImg = param.get("img") == null ? "" : param.get("img").toString().trim();
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

            /////////////////////////////////////////////////
            //保存图片到服务器，
            String fileUrl = AccountValidatorUtil.BASE_IMG_PATH;
            String basePath = servletContext.getRealPath(fileUrl);
            if (!basePath.endsWith("/") && !basePath.endsWith("\\")) { basePath += "/"; }
            //生成文件名
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = sdf.format(new Date());
            String uidStr = "0000000000" + pUid.toString();
            fileName += uidStr.substring(uidStr.length() - 5);

            fileUrl += fileName;
            fileName = basePath + fileName;

            fileUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
                    + httpServletRequest.getContextPath() + fileUrl;

            //注意：传入的文件不含扩展后缀
            StringBuilder fileSuffix = new StringBuilder();
            Base64Convert.generateImageFile(pImg, fileName, fileSuffix);
            fileName += "." + fileSuffix.toString();
            fileUrl += "." + fileSuffix.toString();

            User user = userService.selectByUid(pUid);
            user.setAvatar(fileUrl);
            userService.updateByPrimaryKeySelective(user);

            modelMap.addAttribute("description", "App端修改个人头像，成功！");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("avatar", fileUrl);
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "App端修改个人头像，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

    @ApiOperation(value = "修改头像，头像上传到七牛云服务器")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping(value = "/api/user/profile_picture")
//    public Object uploadProfilePicture(HttpServletRequest request, ModelMap modelMap, @RequestParam("image") MultipartFile image) {
    public Object uploadProfilePicture(HttpServletRequest request, ModelMap modelMap,@RequestBody(required = false) Map<String, Object> param,@RequestParam("image") MultipartFile image)  {
        try {

            User user = TokenManager.getToken();
            String uid = user.getUid().toString();
            String url = "";
            if(param.get("image") != null && param.get("image") != ""){
                String images = param.get("image").toString();
                System.out.println("__________________________________________________" +images);
                url = QiniuUtil.uploadFile(QiniuUtil.generateImage(images), QiniuUtil.BUCKET_NAME_MOTOOLING, "picture", uid);
            }else {
                InputStream is = image.getInputStream();
                 url = QiniuUtil.uploadFile(is, QiniuUtil.BUCKET_NAME_MOTOOLING, "picture", uid);
            }
//            String url = QiniuUtil.uploadFile(ZXingUtils.base64ToInpustStream(image), QiniuUtil.BUCKET_NAME, "picture", request.getParameter("uid").toString());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("avatar_url",url);
            dataMap.put("uid",uid);
            userService.updateAvatar(dataMap);
            return setModelMap(modelMap, HttpCode.OK,dataMap);
        } catch (Exception e){
            logger.error(e.getMessage());
            modelMap.addAttribute("description", "修改个人头像，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}



