package org.rency.common.utils.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RegValidator {
	
	private static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	private static final String REG_PHONE = "^(\\d{3,4}-)?\\d{6,8}$";
	private static final String REG_MOBILE = "^(13|14|15|17|18)\\d{9}$";
	private static final String REG_CARD = "^//d{15}|//d{18}$";
	
	/**
	 * 验证字段长度
	 * @param txt
	 * @param length
	 * @return
	 */
	public static boolean minLength(String txt,int length){
	    if(StringUtils.isBlank(txt)){
	    	return false;
	    }
	    return txt.length() > length; 
	}

	/**
	 * 验证Email
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		return match(REG_EMAIL,email);
	}
	
	/**
	 * 验证电话号码
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone){
	    return match(REG_PHONE,phone);
	}
	
	/**
	 * 验证手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile){
	    return match(REG_MOBILE,mobile);
	}
	
	/**
	 * 验证身份证号
	 * @param cardNo
	 * @return
	 */
	public static boolean idCard(String cardNo){
	    return match(REG_CARD,cardNo);
	}
	
	/**
	* 验证数字输入
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	/**
	* 验证非零的正整数
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsIntNumber(String str) {
		String regex = "^\\+?[1-9][0-9]*$";
		return match(regex, str);
	}

	/**
	* 验证大写字母
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsUpChar(String str) {
		String regex = "^[A-Z]+$";
		return match(regex, str);
	}

	/**
	* 验证小写字母
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsLowChar(String str) {
		String regex = "^[a-z]+$";
		return match(regex, str);
	}

	/**
	* 验证输入字母
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	/**
	* 验证验证输入汉字
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
	}
	
	/**
	* 验证输入邮政编号
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsPostalcode(String str) {
		String regex = "^\\d{6}$";
		return match(regex, str);
	}
	
	/**
	* 验证输入密码长度 (6-18位)
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsPasswLength(String str) {
		String regex = "^\\d{6,18}$";
		return match(regex, str);
	}
	
	/**
	* 验证IP地址
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean isIP(String str) {
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
		return match(regex, str);
	}
	
	/**
	* @param regex
	* 正则表达式字符串
	* @param str
	* 要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	private static boolean match(String regex, String str) {
		if(StringUtils.isBlank(str)){
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(str).matches();
	}
	
	public static void main(String[] args) {
		System.out.println(isPhone("021-22222222"));
		System.out.println(isMobile("17921531110"));
	}
	
}