package com.mt.upms.user.service;


import com.mt.common.core.mybatis.page.Pagination;
import com.mt.common.weixin.model.WeiXinUserInfo;
import com.mt.upms.common.model.*;
import com.mt.upms.permission.bo.URoleBo;
import com.mt.upms.permission.bo.UserRoleAllocationBo;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserService {

	int deleteByPrimaryKey(Long id);

	User insert(User record);

    User insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User login(String email, String password);

	User findUserByEmail(String email);

	Pagination<User> findByPage(Map<String, Object> resultMap, Integer pageNo,
								 Integer pageSize);

	Map<String, Object> deleteUserById(String ids);

	Map<String, Object> updateForbidUserById(Long id, Long status);

	Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap,
													 Integer pageNo, Integer pageSize);

	List<URoleBo> selectRoleByUserId(Long id);

	Map<String, Object> addRole2User(Long userId, String ids);

	Map<String, Object> deleteRoleByUserIds(String userIds);

	///////////////////////////
	User selectByUid(Long uid);

	User selectByMobile(String mobile);

	User selectByWechat(String wechat);

	List<UserCompany> getCompanyByUserId(Long uid);

	void createRegister(User user);

	//获取已生成的短信验证码
	String getCreatedVerifyCode(String phone, String email);

	int createLoginSMS(SmsSend smsSend);

	SmsSend selectLoginSMSByPhone(String phone);

	int deleteLoginSMSByPhone(String phone);

	List<User> selectByDepId(Integer companyId, Integer depId);

	List<Map<String, Object>> getDeptMemeberByDepId(Integer companyId, Integer depId);

	String createSetPasswordEmail(EmailSend emailSend);

	Map<String, Object> checkFindPasswordUrl(String secretUid, String secretEmail, String secretOutTime);

	int insertInvitedUserRecord(InvitedUser invitedUser);

	int insertUserRole(UserRole userRole);

	List<UserRole> selectUserRoleByUid(Integer companyId, Long uid);

	String wechatUploadImg(String imgId, Long uid);

//	// 搜索平台用户  根据USER名字  模糊搜索
//	List<Map<String,Object>> searchUser(String userName);

	Map<String, Object> createUpLoadFileUrl();

	ArrayList<String> downloadPicture(ArrayList<String> urlList);

	String downloadPicture(String urlString);

	int insertMyFriend(MyFriend myFriend);

	MyFriend selectMyFriend(Long myUid, Long friUid);

	//添加微信用户到数据库
	void addUser(WeiXinUserInfo weiXinUserInfo);
	//修改微信用户信息
	void updateUser(WeiXinUserInfo weiXinUserInfo);


	Integer addserWeixiGzh();

	String selectOpenId(String unionId);

	List<Map<String, Object>> getDepIdAndNameByUid(Integer companyId, Long uid);

	int updateAvatar(Map<String,Object> map);

	int setUserNewPassword(Long uid, String newPassword, String vCode, String phone, String email);
}
