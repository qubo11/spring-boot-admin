package com.expect.admin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static final String emailRegex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	public static final String urlRegex = "[a-zA-z]+://[^\\s]*";
	public static final String idCardRegex = "\\d{15}?\\d{18}";
	public static final String phoneRegex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

	/**
	 * 验证email
	 * 
	 * @param email
	 *            电子邮箱
	 * @return 是否匹配
	 */
	public static final boolean email(String email) {
		return regex(emailRegex, email);
	}

	/**
	 * 验证url
	 * 
	 * @param url
	 *            连接
	 * @return 是否哦匹配
	 */
	public static final boolean url(String url) {
		return regex(urlRegex, url);
	}

	/**
	 * 验证身份号码
	 * 
	 * @param idCard
	 *            身份证号码
	 * @return 是否匹配
	 */
	public static final boolean idCard(String idCard) {
		return regex(idCardRegex, idCard);
	}

	/**
	 * 验证手机号码
	 * 
	 * @param phone
	 *            手机号码
	 * @return 是否匹配
	 */
	public static final boolean phone(String phone) {
		return regex(phoneRegex, phone);
	}

	/**
	 * 正则表达式判断
	 * 
	 * @param regEx
	 *            正则表达式
	 * @param str
	 *            需要判断的字符串
	 * @return 是否匹配
	 */
	public static boolean regex(String regEx, String str) {
		if (str == null || str.trim().equals("")) {
			return false;
		}
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

}
