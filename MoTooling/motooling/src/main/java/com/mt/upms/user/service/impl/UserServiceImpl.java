package com.mt.upms.user.service.impl;


import com.mt.cms.common.dao.CompanyMapper;
import com.mt.cms.company.service.CompanyService;
import com.mt.common.bo.AccountValidatorUtil;
import com.mt.common.bo.IdType;
import com.mt.common.bo.WeChatApiUtil;
import com.mt.common.core.mybatis.BaseMybatisDao;
import com.mt.common.core.mybatis.page.Pagination;
import com.mt.common.core.shiro.cache.JedisManager;
import com.mt.common.core.shiro.session.CustomSessionManager;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.utils.LoggerUtils;
import com.mt.common.utils.MathUtil;
import com.mt.common.utils.RedisUtil;
import com.mt.common.weixin.WeixinUtil;
import com.mt.common.weixin.model.WeiXinUserInfo;
import com.mt.upms.common.dao.*;
import com.mt.upms.common.model.*;
import com.mt.upms.permission.bo.URoleBo;
import com.mt.upms.permission.bo.UserRoleAllocationBo;
import com.mt.upms.user.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
/**
* @Author: Fhk
* @Description:
* @create    2018/3/6 10:38
* @Modified By:
*/
@Service
public class UserServiceImpl extends BaseMybatisDao<UserMapper> implements UserService {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	/***
	 * 用户手动操作Session
	 * */
	@Autowired
	private CustomSessionManager customSessionManager;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private SmsSendMapper smsSendMapper;

	@Autowired
	private EmailSendMapper emailSendMapper;

	@Autowired
	private InvitedUserMapper invitedUserMapper;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private  ServletContext servletContext;

	@Autowired
	private JedisManager jedisManager;

	@Autowired
	private MyFriendMapper myFriendMapper;

	@Autowired
	CompanyService companyService;

	@Autowired
	UserWeixiGzhMapper userWeixiGzhMapper;



	@Override
	public int deleteByPrimaryKey(Long id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User insert(User entity) {
		userMapper.insert(entity);
		return entity;
	}

	@Override
	public User insertSelective(User entity) {
		userMapper.insertSelective(entity);
		return entity;
	}

	@Override
	public User selectByPrimaryKey(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(User entity) {
		return userMapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(User entity) {
		return userMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public User login(String email, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", password);
		User user = userMapper.login(map);
		return user;
	}

	@Override
	public User findUserByEmail(String email) {
		return userMapper.findUserByEmail(email);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<User> findByPage(Map<String, Object> resultMap,
									   Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> deleteUserById(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String[] idArray = new String[]{};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[]{ids};
			}

			for (String id : idArray) {
				count += this.deleteByPrimaryKey(new Long(id));
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateForbidUserById(Long id, Long status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = selectByPrimaryKey(id);
			//user.setStatus(status);
			updateByPrimaryKeySelective(user);

			//如果当前用户在线，需要标记并且踢出
			customSessionManager.forbidUserById(id, status);


			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败，请刷新再试！");
			LoggerUtils.fmtError(getClass(), "禁止或者激活用户登录失败，id[%s],status[%s]", id, status);
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap,
															Integer pageNo, Integer pageSize) {
		return super.findPage("findUserAndRole", "findCount", modelMap, pageNo, pageSize);
	}

	@Override
	public List<URoleBo> selectRoleByUserId(Long id) {
		return userMapper.selectRoleByUserId(id);
	}

	@Override
	public Map<String, Object> addRole2User(Long userId, String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			//先删除原有的。
			userRoleMapper.deleteByUserId(userId);
			//如果ids,role 的id 有值，那么就添加。没值象征着：把这个用户（userId）所有角色取消。
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = null;

				//这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if (StringUtils.contains(ids, ",")) {
					idArray = ids.split(",");
				} else {
					idArray = new String[]{ids};
				}
				//添加新的。
				for (String rid : idArray) {
					//这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的}
					if (StringUtils.isNotBlank(rid)) {
						UserRole entity = new UserRole(userId, new Long(rid));
						count += userRoleMapper.insertSelective(entity);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		//清空用户的权限，迫使再次获取权限的时候，得重新加载
		TokenManager.clearUserAuthByUserId(userId);
		resultMap.put("count", count);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByUserIds(String userIds) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("userIds", userIds);
			userRoleMapper.deleteRoleByUserIds(resultMap);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		return resultMap;

	}

	@Override
	public User selectByUid(Long uid) {
		return userMapper.selectByUid(uid);
	}

	@Override
	public User selectByMobile(String mobile) {
		return userMapper.selectByMobile(mobile);
	}

	/**
	 * 获取用户公司
	 * @param uid
	 * @return
	 */
	@Override
	public List<UserCompany> getCompanyByUserId(Long uid) {
		return companyMapper.selectByUserId(uid);
	}

	@Override
	public void createRegister(User user) {
		userMapper.register(user);
	}

	@Override
	public User selectByWechat(String wechat) {
		return userMapper.selectByWechat(wechat);
	}

	/**
	 * 获取已生成的验证码
	 * @param phone: phone,email 二选一，空无效，非空有效
	 * @param email
	 * @return
	 */
	@Override
	public String getCreatedVerifyCode(String phone, String email) {
		//放后实现, 如果验证码超时失效，返回空串“”
		if (phone == null || phone.isEmpty()) { phone = email; }
		SmsSend smsSend = smsSendMapper.selectByPhone(phone);
		if (smsSend != null) {
			Date now = new Date();
			Long t1 = (now.getTime() - smsSend.getCreatedAt().getTime()) / 1000;
			if (t1 > AccountValidatorUtil.VERIFY_CODE_TIMEOUT) {
				return "";
			} else {
				return smsSend.getMessage().trim();
			}
		} else {
			return "";
		}
	}

	/**
	 * 将生成的登录短信保存到数据库
	 * @param smsSend
	 * @return
	 */
	@Override
	public int createLoginSMS(SmsSend smsSend) {
		this.deleteLoginSMSByPhone(smsSend.getPhone().trim());
		return smsSendMapper.createLoginSMS(smsSend);
	}

	@Override
	public SmsSend selectLoginSMSByPhone(String phone) {
		return smsSendMapper.selectByPhone(phone);
	}

	@Override
	public int deleteLoginSMSByPhone(String phone) {
		return smsSendMapper.deleteByPhone(phone);
	}

	/**
	 * 获取部门人员
	 * @param companyId
	 * @param depId
	 * @return
	 */
	@Override
	public List<User>  selectByDepId(Integer companyId, Integer depId) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("companyId", companyId);
		dataMap.put("depId", depId);
		return userMapper.selectByDepId(dataMap);
	}

	@Override
	public List<Map<String, Object>> getDeptMemeberByDepId(Integer companyId, Integer depId) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("companyId", companyId);
		dataMap.put("depId", depId);
		return userMapper.getDeptMemeberByDepId(dataMap);
	}
	/**
	 * 生成密码验证邮件
	 * @param emailSend
	 * @return
	 */
	@Override
	public String createSetPasswordEmail(EmailSend emailSend) {

		//生成密钥

		String secretKey = UUID.randomUUID().toString();
		//设置过期时间
		Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
		System.out.println(System.currentTimeMillis());
		long date = outDate.getTime() / 1000 * 1000;	// 忽略毫秒数  mySql 取出时间是忽略毫秒数的

		//此处应该更新Studentinfo表中的过期时间、密钥信息
		emailSend.setOuttime(date);
		emailSend.setSecretKey(secretKey);

		//将用户名、过期时间、密钥生成链接密钥
		String secretUid = MathUtil.getMD5(emailSend.getUid().toString().trim());
		String secretOutTime = MathUtil.getMD5(Long.toString(date).trim());
		String secretEmail = MathUtil.getMD5(emailSend.getEmail().trim());
		emailSend.setMessage(secretUid + "|" + secretEmail + "|" + secretOutTime);
		emailSendMapper.insertSelective(emailSend);

		//String path = httpServletRequest.getContextPath();//.getRequestURL();
		//String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() +":"+ httpServletRequest.getServerPort() + path;
		//String resetPassHref = basePath + "/api/user/" + AccountValidatorUtil.RESET_LOGIN_PASSWORD + "?u=" + secretUid + "&e=" + secretEmail + "&t=" + secretOutTime;
		String resetPassHref = AccountValidatorUtil.RESET_LOGIN_PASSWORD + "?u=" + secretUid + "&e=" + secretEmail + "&t=" + secretOutTime;
		String emailContent = AccountValidatorUtil.SET_PASSWORD_EMAIL_TEMPLET;
		emailContent = emailContent.replace("<<url>>", resetPassHref);

		return emailContent;
	}

	/**
	 * 该方法用于处理从邮箱链接过来的修改密码请求
	 * @param secretUid
	 * @param secretEmail
	 * @param secretOutTime
	 * @return
	 */
	@Override
	public Map<String, Object> checkFindPasswordUrl(String secretUid, String secretEmail, String secretOutTime) {

		Map<String, Object>retMap = new HashMap<>();
		retMap.put("code", 0);
		//Studentinfo stu=studentinfoService.getStudentinfoById(Long.parseLong(id));
		String secretInfo = secretUid + "|" + secretEmail.trim() + "|" + secretOutTime;
		EmailSend emailSend = emailSendMapper.selectPasswordEmail(secretInfo);
		if(emailSend == null){
			retMap.put("code", 0);
			retMap.put("message", "不正确的链接！");
			return retMap;
		}

		//获取当前用户申请找回密码的过期时间
		//找回密码链接已经过期
		if(emailSend.getOuttime()<=System.currentTimeMillis()){
			System.out.println("0链接已经过期");
			retMap.put("code", 0);
			retMap.put("message", "链接已经过期！");
			retMap.put("uid", emailSend.getUid());
			retMap.put("email", emailSend.getEmail());
			return retMap;
		}
		//// 数字签名
		String digitalSignature = MathUtil.getMD5(Long.toString(emailSend.getUid()).trim()) + "|" +
			MathUtil.getMD5(emailSend.getEmail().trim()) + "|" +
			MathUtil.getMD5(Long.toString(emailSend.getOuttime()).trim());

		if(secretInfo.equals(digitalSignature)) {
			retMap.put("code", 1);
			retMap.put("message", "成功！");
			retMap.put("uid", emailSend.getUid());
			retMap.put("email", emailSend.getEmail());
			return retMap;
		}
		else {
			retMap.put("code", 0);
			retMap.put("message", "链接加密密码不正确！");
			retMap.put("uid", emailSend.getUid());
			retMap.put("email", emailSend.getEmail());
			return retMap;
		}
	}

	@Override
	public int insertInvitedUserRecord(InvitedUser invitedUser){
		return invitedUserMapper.insertSelective(invitedUser);
	}

	@Override
	public int insertUserRole(UserRole userRole) {
		return  userRoleMapper.insertSelective(userRole);
	}

	/**
	 * 根据Uid获取用户角色信息
	 * @param companyId
	 * @param uid
	 * @return
	 */
	@Override
	public List<UserRole> selectUserRoleByUid(Integer companyId, Long uid) {
		Map<String, Object>dataMap = new HashMap<>();
		dataMap.put("companyId", companyId);
		dataMap.put("uid", uid);
		return userRoleMapper.selectByCompanyIdAndUid(dataMap);
	}

	/**
	 * 将微信端图片上载到系统服务器
	 * @param imgId
	 * @param uid
	 * @return
	 */
	@Override
	public  String wechatUploadImg(String imgId, Long uid) {
		//保存图片到服务器，
		String fileUrl = AccountValidatorUtil.BASE_IMG_PATH;
		String basePath = servletContext.getRealPath(fileUrl);
		if(!basePath.endsWith("/") && !basePath.endsWith("\\")) { basePath += "/"; }

		//生成文件名
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date());
		if(uid != null) {
			String uidStr = "0000000000" + uid.toString();
			fileName += uidStr.substring(uidStr.length() - 5);
		}
		//文件类型要识别
		fileName += ".jpg";
		fileUrl += fileName;
		fileName = basePath + fileName;

		Jedis jedis = RedisUtil.getJedis();
		String token = jedis.get(AccountValidatorUtil.WECHAT_IMAGE_TOKEN_NAME);
		if(token == null){
			token = WeChatApiUtil.getToken(AccountValidatorUtil.APPID, AccountValidatorUtil.APPSECRET);
			jedis.set(AccountValidatorUtil.WECHAT_IMAGE_TOKEN_NAME, token);
			jedis.expire(AccountValidatorUtil.WECHAT_IMAGE_TOKEN_NAME, 7000);	//<7200
		}
		if(token == null) { return  null; }

		//下载刚刚上传的图片以id命名
		File t = WeChatApiUtil.downloadMedia(fileName, token, imgId);
		String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() +":"+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath();

		return baseUrl + fileUrl;
	}
//
//	// 搜索平台用户    根据USER名字  模糊搜索
//	@Override
//	public List<Map<String, Object>> searchUser(String userName) {
//		return userMapper.searchUser(userName);
//	}

	/**
	 * 生成上载随机文件名及url，不带后缀
	 * @return
	 */
	@Override
	public Map<String, Object> createUpLoadFileUrl() {
		String fileUrl = AccountValidatorUtil.BASE_IMG_PATH;
		String basePath = servletContext.getRealPath(fileUrl);
		if (!basePath.endsWith("/") && !basePath.endsWith("\\")) { basePath += "/"; }

		//生成文件名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date());
		String random = "0000000000" + RandomUtils.nextLong(0, 9999999999L);
		fileName += random.substring(random.length() - 5);

		fileUrl += fileName;
		fileName = basePath + fileName;

		Map<String, Object> retMap = new HashMap<>();
		retMap.put("url", fileUrl);
		retMap.put("file", fileName);
		return retMap;
	}

	/**
	 * 根据url下载图片到服务器
	 * @param urlList
	 * @return
	 */
	@Override
	public ArrayList<String> downloadPicture(ArrayList<String> urlList) {
		URL url = null;
		ArrayList<String> retUrl = new ArrayList<>();
		for (String urlString : urlList) {
			try {
				url = new URL(urlString);
				DataInputStream dataInputStream = new DataInputStream(url.openStream());

				Map map = createUpLoadFileUrl();
				String imageName = map.get("file").toString();
				retUrl.add(map.get("url").toString());

				FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

				byte[] buffer = new byte[1024];
				int length;

				while ((length = dataInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, length);
				}
				dataInputStream.close();
				fileOutputStream.close();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retUrl;
	}

	/**
	 * 根据url下载图片到服务器,生成图片文件及新的url
	 * @param urlString
	 * @return
	 */
	@Override
	public String downloadPicture(String urlString) {
		URL url = null;

		Map map = this.createUpLoadFileUrl();
		String fileUrl = map.get("url").toString() + ".jpg";
		String fileName = map.get("file").toString() + ".jpg";
		String imageName = "";
		try {
			url = new URL(urlString);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			imageName = fileName;
			FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			dataInputStream.close();
			fileOutputStream.close();

			return fileUrl;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public int insertMyFriend(MyFriend myFriend){
		return myFriendMapper.insertSelective(myFriend);

	}

	/**
	 * 获取我的某个朋友信息
	 * @param myUid
	 * @param friUid
	 * @return
	 */
	@Override
	public  MyFriend selectMyFriend(Long myUid, Long friUid) {
		Map<String, Object>map = new HashMap<>();
		map.put("myUid", myUid);
		map.put("friUid", friUid);
		return myFriendMapper.selectMyFriend(map);
	}

	@Override
	public void addUser(WeiXinUserInfo wx) {
		logger.error(wx.getOpenId());
		User user = new User();
		Long uid = companyService.createNewId(IdType.USER);
		user.setUid(uid);
		user.setWechat(wx.getUnionid());
		user.setOpenId(wx.getOpenId());
		user.setGender(Byte.parseByte(wx.getSex()+""));
		user.setFullname(wx.getNickname());
		user.setUsername(wx.getNickname());
		user.setCity(wx.getCity());
		user.setProvince(wx.getProvince());
		user.setCountry(wx.getCountry());
//		String fileUrl = this.downloadPicture(wx.getHeadImgUrl());
//		user.setAvatar(fileUrl);
		user.setAvatar(wx.getHeadImgUrl());
		this.insertSelective(user);
	}

	/**
	 * 微信 更新修改用户 信息
	 * @param wx
	 */
	@Override
	public void updateUser(WeiXinUserInfo wx) {
		User user = new User();
		Long uid = companyService.createNewId(IdType.USER);
		user.setUid(uid);
		user.setOpenId(wx.getOpenId());
		user.setWechat(wx.getUnionid());
		user.setGender(Byte.parseByte(wx.getSex()+""));
		user.setFullname(wx.getNickname());
		user.setCity(wx.getCity());
		user.setProvince(wx.getProvince());
		user.setCountry(wx.getCountry());
		String fileUrl = this.downloadPicture(wx.getHeadImgUrl());
		user.setAvatar(fileUrl);
		this.updateByPrimaryKeySelective(user);
	}

	@Override
	public Integer addserWeixiGzh() {
		List<String> list = WeixinUtil.getopenIdList();
		List<String> openIdList = userWeixiGzhMapper.selectOpenIdList();
			for(String s:list){
				Map<String, Object> map = WeixinUtil.oneWeiXinUser(s);
				log.info("获取的微信用户参数 {}",JSONObject.fromObject(map));
				int i = userWeixiGzhMapper.addserWeixiGzh(map);
		}
		return 0000;
	}

	@Override
	public String selectOpenId(String unionId) {
		return  userWeixiGzhMapper.selectOpenId(unionId);
	}

	@Override
	public List<Map<String, Object>> getDepIdAndNameByUid(Integer companyId, Long uid) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("uid", uid);
		return  userMapper.getDepIdAndNameByUid(map);
	}

	/**
	 * 修改头像
	 * @param map
	 * @return
	 */
	@Override
	public int updateAvatar(Map<String, Object> map) {
		return userMapper.updateAvatar(map);
	}

	/**
	 * 通过邮箱或手机验证码重设密码，不需要验证旧密码
	 * @param uid
	 * @param newPassword
	 * @param vCode
	 * @return
	 */
	@Override
	public int setUserNewPassword(Long uid, String newPassword, String vCode, String phone, String email) {
		String createdCode = this.getCreatedVerifyCode(phone, email);
		if (!createdCode.equals(vCode)) {
			throw new IllegalArgumentException("验证码不正确！");
		}
		User user = this.selectByUid(uid);
		if (user == null) {
			throw new IllegalArgumentException("用户不存在！");
		}
		user.setPassword(newPassword);
		return userMapper.updateByPrimaryKeySelective(user);
	}
}
