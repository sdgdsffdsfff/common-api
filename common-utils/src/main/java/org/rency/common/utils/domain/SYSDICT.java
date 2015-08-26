package org.rency.common.utils.domain;

import java.util.regex.Pattern;

public class SYSDICT {
	/**
	 * 系统默认字符集
	 */
	public static final String CHARSET = "UTF-8";
	
	/**
	 * SESSION用户标识
	 */
	public static final String SESSION_USER_KEY = "_current_user";
	
	/**
	 * SESSION超时提示
	 */
	public static final String SESSION_TIMEOUT_INFO = "会话超时，请重新登录！";
	
	/**
	 * SESSION超时KEY
	 */
	public static final String SESSION_TIMEOUT_KEY = "_timeout";
	
	/**
	 * 访问拒绝KEY
	 */
	public static final String RESOURCES_DENIED_ACCESS_KEY = "_denied";
	
	/**
	 * 访问拒绝提示
	 */
	public static final String RESOURCES_DENIED_ACCESS = "抱歉，权限不足！";
	
	/**
	 * 分隔符
	 */
	public static final String SPLIT_KEY = "-";
	
	/**
	 * URL正则表达式
	 */
	public static final Pattern PATTERN_URL = Pattern.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",Pattern.CASE_INSENSITIVE);
	
	/**
	 * 回调路径KEY
	 */
	public static final String URL_PARAM_CALLBACK_KEY = "?callback=";
	
	/**
	 * kaptcha生成session 验证码 key
	 */
	public static final String SESSION_KAPTCHA_KEY = "kaptcha";
}
