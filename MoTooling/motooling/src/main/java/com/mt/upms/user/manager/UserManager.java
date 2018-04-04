package com.mt.upms.user.manager;



import com.mt.common.utils.MathUtil;
import com.mt.upms.common.model.Permission;
import com.mt.upms.common.model.Role;
import com.mt.upms.common.model.User;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserManager {

	/**
	 * 加工密码，和登录一致。
	 * @param user
	 * @return
	 */
	public static User md5Pswd(User user){
		//密码为   email + '#' + pswd，然后MD5
		//user.setPassword(md5Pswd(user.getEmail(),user.getPassword()));
		String account = "";
		if(user.getEmail() != null && !user.getEmail().trim().equals(""))
			account = user.getEmail().trim();
		else if(user.getMobile() != null && !user.getMobile().trim().equals(""))
			account = user.getMobile().trim();
		else if(user.getWechat() != null && !user.getWechat().trim().equals(""))
			account = user.getWechat().trim();
		else
			return null;

		user.setPassword(md5Pswd(account,user.getPassword()));
		return user;
	}
	/**
	 * 字符串返回值
	 * @param email
	 * @param pswd
	 * @return
	 */
	public static String md5Pswd(String email ,String pswd){
		pswd = String.format("%s#%s", email,pswd);
		pswd = MathUtil.getMD5(pswd);
		return pswd;
	}

	/**
	 * 用固定字串与password一起加密
	 */
	public static String md5Pswd(String pswd){
		pswd = String.format("%s#%s", "motooling",pswd);
		pswd = MathUtil.getMD5(pswd);
		return pswd;
	}
	/**
	 * 把查询出来的roles 转换成bootstarp 的 tree数据
	 * @param roles
	 * @return
	 */
	public static List<Map<String,Object>> toTreeData(List<Role> roles){
		List<Map<String,Object>> resultData = new LinkedList<Map<String,Object>>();
		for (Role u : roles) {
			//角色列表
			Map<String,Object> map = new LinkedHashMap<String, Object>();
			map.put("text", u.getRoleName());//名称
			map.put("href", "javascript:void(0)");//链接
			List<Permission> ps = u.getPermissions();
			map.put("tags",  new Integer[]{ps.size()});//显示子数据条数
			if(null != ps && ps.size() > 0){
				List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
				//权限列表
				for (Permission up : ps) {
					Map<String,Object> mapx = new LinkedHashMap<String, Object>();
					mapx.put("text", up.getPermName());//权限名称
					//需要设置可以访问的资源url
					/**-------TODO---------**/
					//mapx.put("href", up.getUrl());//权限url
					//mapx.put("tags", "0");//没有下一级
					list.add(mapx);
				}
				map.put("nodes", list);
			}
			resultData.add(map);
		}
		return resultData;

	}


}
