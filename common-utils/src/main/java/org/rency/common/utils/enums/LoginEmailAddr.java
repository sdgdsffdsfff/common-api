package org.rency.common.utils.enums;

/**
 * 邮箱服务器登录地址
 * @author rencaiyu
 *
 */
public enum LoginEmailAddr {

	/** 网易163邮箱 */
    EMAIL_163("163.com", "http://mail.163.com"),

    /** 网易126邮箱 */
    EMAIL_126("126.com", "http://www.126.com/"),

    /** 网易yeah邮箱 */
    EMAIL_YEAH("yeah.net", "http://www.yeah.net/"),

    /** qq邮箱 */
    EMAIL_QQ("qq.com", "http://mail.qq.com"),

    /** qq企业邮箱 */
    EMAIL_NETFINWORKS("netfinworks.com", "http://exmail.qq.com/login"),

    /** qq邮箱 */
    EMAIL_FOXMAIL("foxmail.com", "http://mail.qq.com"),
    
    EMAIL_139("139.com", "http://mail.10086.cn/"),
    
    EMAIL_GMAIL("gmail.com", "http://accounts.google.com/"),
    
    EMAIL_SOGOU("sogou.com", "http://mail.sogou.com/"),
    
    EMAIL_SOHU("sohu.com", "http://mail.sohu.com/"),
    
    EMAIL_SINA_COM("sina.com", "http://mail.sina.com.cn/"),
    
    EMAIL_SINA_CN("sina.cn", "http://mail.sina.com.cn/"),
    
    EMAIL_2980("2980.com", "http://www.2980.com/"),
    
    EMAIL_WO("wo.cn", "http://mail.wo.com.cn"),
    
    EMAIL_OUTLOOK("outlook.com", "http://login.live.com/"),
    
    EMAIL_HOTMAIL("hotmail.com", "http://login.live.com/"),
    
    EMAIL_189("189.cn", "http://webmail2.189.cn"),
    
    EMAIL_ALIYUN("aliyun.com", "http://mail.aliyun.com"),
    
    EMAIL_188("188.com", "http://www.188.com/"),
    
    EMAIL_TOM("tom.com", "http://web.mail.tom.com");

    /** 邮箱名称 */
    private final String emailName;

    /** 邮箱登录地址 */
    private final String url;

    LoginEmailAddr(String emailName, String url) {
        this.emailName = emailName;
        this.url = url;
    }

    public String getEmailName() {
        return emailName;
    }

    public String getUrl() {
        return url;
    }

    public static String getEmailLoginUrl(String emailName) {
        emailName = emailName.substring(emailName.lastIndexOf("@") + 1);
        for (LoginEmailAddr item : LoginEmailAddr.values()) {
            if (item.getEmailName().equals(emailName)) {
                return item.getUrl();
            }
        }
        return "";
    }
	
    public boolean equals(String code) {
        return getEmailName().equals(code);
    }
}