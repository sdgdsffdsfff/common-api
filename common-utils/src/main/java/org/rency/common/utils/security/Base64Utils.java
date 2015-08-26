package org.rency.common.utils.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
* @ClassName: BASE64Utils 
* @Description: BASE64Utils加密解密算法
* @Author user_rcy@163.com
* @Date 2015年3月8日 下午9:52:20 
*
 */
public class Base64Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);
	
	private final static String CHARSET = "UTF-8";
	private static BASE64Encoder encoder = new BASE64Encoder(); 
	
	private static BASE64Decoder decoder = new BASE64Decoder(); 
	
	/**
	* @Title: decoder 
	* @Description: BASE64解密
	* @Date: 2015年3月8日 下午9:59:39
	* @param key
	* @return
	 * @throws IOException 
	 */
	public static String decoder(String content) throws IOException{
		String s = new String(decoder.decodeBuffer(content));
        logger.debug("Base64 decoder{} ->{} ",content,s);
        return s;
	}
	
	/**
	* @Title: encoder 
	* @Description: BASE64加密
	* @Date: 2015年3月8日 下午10:01:48
	* @param key
	* @return
	 */
	public static String encoder(String content){
		try {
			byte[] b = content.getBytes(CHARSET);
			return encoder(b);
		} catch (UnsupportedEncodingException e) {
			logger.error("Base64Utils 转码异常.",e);
			return null;
		}  
         
	}
	
	/**
	* @Title: encoder 
	* @Description: BASE64加密
	* @Date: 2015年3月8日 下午10:01:48
	* @param key
	* @return
	 */
	public static String encoder(byte[] content){
		String s = encoder.encode(content);
        logger.debug("Base64 encoder{} ->{} ",content,s);
        return s; 
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(encoder("rency"));
		System.out.println(decoder("cmVuY3k="));
	}
	
}