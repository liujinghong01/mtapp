package com.mt.common.core.support;


import com.mt.common.core.config.Resources;

/**
 * Ajax 请求时的自定义查询状态码，主要参考Http状态码，但并不完全对应
 *
 */
public enum HttpCode {
	/** 200请求成功 */
	OK("00000"),
	/** 无效参数，请求失败 */
	FAIL("11111"),
	/** 用户未登录 */
	NOT_LOGIN("50000"),
	/** 207频繁操作 */
	MULTI_STATUS("207"),
	/** 400请求参数出错 */
	BAD_REQUEST("400"),
	/** 验证码不正确 */
	BAD_VCODE("10001"),
	/** 帐号|Email已经存在！ */
	BAD_EMAIL("10002"),
	/** 303登录失败 */
	LOGIN_FAIL("10003"),
	/** UID 无效 */
	BAD_UID("10004"),
	/** 用户存在 **/
	USER_EXISTS("10020"),
	/** 用户不存在 **/
	USER_NOT_EXISTS("10021"),
	/** 401没有登录 */
	UNAUTHORIZED("50000"),
	/** 403没有权限 */
	FORBIDDEN("403"),
	/** 404找不到页面 */
	NOT_FOUND("404"),
	/** 408请求超时 */
	REQUEST_TIMEOUT("408"),
	/** 409发生冲突 */
	CONFLICT("409"),
	/** 410已被删除 */
	GONE("410"),
	/** 423已被锁定 */
	LOCKED("423"),
	/** 500服务器出错 */
	INTERNAL_SERVER_ERROR("500"),
	/** 50001：微信首次登錄，要求前端調用綁定接口，確認是否綁定原有帳戶 */
	WECHAT_BIND_ORIG_ACCOUNT("50001");

	private final String value;

	private HttpCode(String value) {
		this.value = value;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public String value() {
		return this.value;
	}

	public String msg() {
		return Resources.getMessage("HTTPCODE_" + this.value);
	}

	public String toString() {
		return this.value.toString();
	}
}
