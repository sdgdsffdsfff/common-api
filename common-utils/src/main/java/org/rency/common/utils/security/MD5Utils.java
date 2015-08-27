package org.rency.common.utils.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.rency.common.utils.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
public class MD5Utils {
	private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);
    /** 
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合 
     */  
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    protected static MessageDigest messagedigest = null;
    
    static {  
        try {  
            messagedigest = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException nsaex) {  
        	logger.error(MD5Utils.class.getName()+ "初始化失败，MessageDigest不支持MD5Util。");  
            nsaex.printStackTrace();  
        }  
    }  
      
    /** 
     * 生成字符串的md5校验值 
     *  
     * @param s 
     * @return 
     */  
    public static String MD5(String s) {  
        return getMD5String(s.getBytes()).toUpperCase();  
    }  
      
    /** 
     * 判断字符串的md5校验码是否与一个已知的md5码相匹配 
     *  
     * @param password 要校验的字符串 
     * @param md5PwdStr 已知的md5校验码 
     * @return 
     */  
    public static boolean checkPassword(String password, String md5PwdStr) {  
        String s = MD5(password);  
        return s.equals(md5PwdStr);  
    }  
      
    /** 
     * 生成文件的md5校验值 
     *  
     * @param file 
     * @return 
     * @throws IOException 
     */  
    public static String getFileMD5String(File file) throws IOException {         
        InputStream fis;  
        fis = new FileInputStream(file);  
        byte[] buffer = new byte[1024];  
        int numRead = 0;  
        while ((numRead = fis.read(buffer)) > 0) {  
            messagedigest.update(buffer, 0, numRead);  
        }  
        fis.close();  
        return bufferToHex(messagedigest.digest()).toUpperCase();  
    }  
  
    public static String getMD5String(byte[] bytes) {  
        messagedigest.update(bytes);  
        return bufferToHex(messagedigest.digest()).toUpperCase();  
    }  
  
    /**
     * byte[]转换为字符串
     * @param bytes
     * @return
     */
    public static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }  
  
    private static String bufferToHex(byte bytes[], int m, int n) {  
        StringBuffer stringbuffer = new StringBuffer(2 * n);  
        int k = m + n;  
        for (int l = m; l < k; l++) {  
            appendHexPair(bytes[l], stringbuffer);  
        }  
        return stringbuffer.toString();  
    }  
  
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同   
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换   
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    }  
    
    /**
     * SHA加密
     * @param key
     * @return
     * @throws CoreException
     */
	public static String SHA(String key) throws CoreException{
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA");
			sha.update(key.getBytes());
			return  bufferToHex(sha.digest()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error("SHA加密异常.",e);
			throw new CoreException(e);
		}
	}
	
	/**
	 * SHA-1加密
	 * @param key
	 * @return
	 * @throws CoreException
	 */
	public static String SHA1(String key) throws CoreException{
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(key.getBytes());
			return  bufferToHex(sha.digest()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error("SHA-1加密异常.",e);
			throw new CoreException(e);
		}
	}
	
	/**
	 * SHA-256加密
	 * @param key
	 * @return
	 * @throws CoreException
	 */
	public static String SHA256(String key) throws CoreException{
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(key.getBytes());
			return  bufferToHex(sha.digest()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error("SHA-256加密异常.",e);
			throw new CoreException(e);
		}
	}
	
	public static void main(String[] args) throws CoreException {
		System.out.println("MD5="+MD5("rency"));
		System.out.println("SHA="+SHA("rency"));
		System.out.println("MD5+SHA="+MD5(MD5("rency")+SHA("rency")));
		System.out.println("SHA1="+SHA1("rency"));
		System.out.println("SHA256="+SHA256("rency"));
	}
}  