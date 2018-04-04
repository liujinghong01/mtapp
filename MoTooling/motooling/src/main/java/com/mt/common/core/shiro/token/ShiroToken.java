package com.mt.common.core.shiro.token;

import com.mt.upms.common.model.User;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 * Shiro token
 *
 */
public class ShiroToken extends UsernamePasswordToken  implements java.io.Serializable{
	
	private static final long serialVersionUID = -6451794657814516274L;
	/** 登录密码[字符串类型] 因为父类是char[] ] **/
	private String pswd ;
	private User tokenUser;

	public ShiroToken(String account, String password) {
		super(account, password);
		this.pswd = password ;
	}

	public ShiroToken(String account, String password, User user) {
		super(account, password);
		this.pswd = password ;
		this.tokenUser = user;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public User getTokenUser() { return this.tokenUser; }
	public void setTokenUser(User tokenUser) { this.tokenUser = tokenUser; }
	
	
	
}
