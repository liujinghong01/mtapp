package com.mt.upms.user.controller.app;


import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.upms.user.service.MyFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
* @Author: Fhk
* @Description:
* @create    2018/3/6 10:36
* @Modified By:
*/
@RestController
@Api(value = "查看朋友信息", description = "查看朋友信息")
public class UserMyFriendController extends BaseController {

    @Autowired
    private MyFriendService myFriendService;

    @ApiOperation(value = "查看朋友资料", notes = "")
    @PostMapping("/api/friend/view")
    public Object lookMyFeiendDetails(@RequestBody Map<String,Object> param, ModelMap modelMap){
        try {

            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString();
            Long friUid = param.get("fri_uid") == null ? null : Long.valueOf(param.get("fri_uid").toString());
            Integer friComId = param.get("fri_com_id") == null ? null : Integer.valueOf(param.get("fri_com_id").toString());
            Long uId = param.get("uid") == null ? null : Long.valueOf(param.get("uid").toString());

            Map<String,Object> map = myFriendService.friendView(uId, friComId, friUid);
            if(map==null){
                modelMap.addAttribute("description", "参数错误，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            modelMap.addAttribute("description", "查看朋友资料，成功！");
            return setModelMap(modelMap, HttpCode.OK,map);

        }catch (Exception ex){
            modelMap.addAttribute("description", "查看朋友资料，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "查询我的朋友列表", notes = "")
    @PostMapping("/api/friend/list")
    public Object lookMyFeiend(@RequestBody Map<String,Object> param, ModelMap modelMap){
        try {

            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString();
            Long uId = param.get("uid") == null ? null : Long.valueOf(param.get("uid").toString());

            Map<String,Object> map = myFriendService.friendList(uId);
            modelMap.addAttribute("description", "查询我的朋友列表，成功！");
            return setModelMap(modelMap, HttpCode.OK,map);
        }catch (Exception ex){
            modelMap.addAttribute("description", "用户不存在！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
