package org.rency.common.utils.security;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
* @ClassName: DESUtils 
* @Description: DES加密解密算法(对称)
* @Author user_rcy@163.com
* @Date 2015年3月8日 下午10:29:45 
*
 */
public class DESUtils {
	
	private final static String DES_ALGORITHM = "DES";
	    
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
          
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }


	public static void main(String[] args) throws Exception {
		System.out.println(encrypt("rency","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWSmemWPnhGhu5IPfnGbDPwQLpgiNGb9oQIXZ/rzWEPJ+3XUAbpLygfoKfFJ6bh7yN4QAgB9dimfcUYM6jYIA+k7H0jA3N4Eq1/w5y26cIgkTuVCUySBxj4u3CbmqhhOYiFADGsxQqqUGyhcDQV+m2tT/7DwC5BHY2kU3oWJ64fQIDAQAB"));
		System.out.println(decrypt("gNl0nOmN3S0=","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWSmemWPnhGhu5IPfnGbDPwQLpgiNGb9oQIXZ/rzWEPJ+3XUAbpLygfoKfFJ6bh7yN4QAgB9dimfcUYM6jYIA+k7H0jA3N4Eq1/w5y26cIgkTuVCUySBxj4u3CbmqhhOYiFADGsxQqqUGyhcDQV+m2tT/7DwC5BHY2kU3oWJ64fQIDAQAB"));
	}
	
}