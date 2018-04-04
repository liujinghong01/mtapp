package com.mt.upms.common.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mt.common.core.base.BaseModel;

import java.io.Serializable;

/**
 * <p>
 * 邮件表
 * </p>
 */
@SuppressWarnings("serial")
@TableName("sys_email")
public class SysEmail extends BaseModel {
	/**
	 * 邮件名称
	 */
	@TableField(value = "email_name")
	private String emailName;
	/**
	 * 使用发送
	 */
	@TableField(value = "sender")
	private String sender;
	/**
	 * 发送标题
	 */
	@TableField(value = "email_title")
	private String emailTitle;
	/**
	 * 发送内容
	 */
	@TableField(value = "email_content")
	private String emailContent;

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	protected Serializable pkVal() {
		return getId();
	}

}
