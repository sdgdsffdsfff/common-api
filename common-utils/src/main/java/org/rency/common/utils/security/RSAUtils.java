package org.rency.common.utils.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.rency.common.utils.exception.CoreException;

/**
 * 
* @ClassName: RSAUtils 
* @Description: RSA加密解密算法(非对称)
* @Author user_rcy@163.com
* @Date 2015年3月8日 下午10:30:13 
*
 */
public class RSAUtils {
	
	private static final String KEY_ALGORITHM = "RSA";//加密算法RSA
	private static final int KEY_LENGTH = 1024;//密钥长度
	
	/** 生成密钥对(公钥和私钥) */
    public static KeyPair genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_LENGTH);
        return keyPairGen.generateKeyPair();
    }
	
	/**
	 * 
	* @Title: encryptRSAWithPublicKey 
	* @Description: RSA公钥加密
	* @Date: 2015年3月8日 下午10:35:01
	* @param content
	* @param publicKey 公钥
	* @return
	* @throws CoreException
	 */
	public static String encryptRSAWithPublicKey(String content,String publicKey) throws CoreException{
		try{
			byte[] keyBytes = Base64Utils.decoder(publicKey).getBytes();
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes); // 取得公钥  
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key key = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return new String(cipher.doFinal(content.getBytes()));
		}catch(Exception e){
			throw new CoreException(e);
		}
	}

	/**
	 * 
	* @Title: encryptRSAWithPublicKey 
	* @Description: RSA私钥加密
	* @Date: 2015年3月8日 下午10:35:01
	* @param content
	* @param privateKey 私钥
	* @return
	* @throws CoreException
	 */
	public static String encryptRSAWithPrivateKey(String content,String privateKey) throws CoreException{
		try{
			byte[] keyBytes = Base64Utils.decoder(privateKey).getBytes();
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes); // 取得私钥  
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key key = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return new String(cipher.doFinal(content.getBytes()));
		}catch(Exception e){
			throw new CoreException(e);
		}
	}
	
	/**
	 * 
	* @Title: decryptWithPublicKey 
	* @Description: RSA公钥解密
	* @Date: 2015年3月8日 下午10:55:52
	* @param content
	* @param publicKey
	* @return
	* @throws CoreException
	 */
	public static String decryptWithPublicKey(String content, String publicKey)throws CoreException {  
		try{
			// 对密钥解密  
	        byte[] keyBytes = Base64Utils.decoder(publicKey).getBytes();  
	        // 取得公钥  
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key key = keyFactory.generatePublic(x509KeySpec);  
	        // 对数据解密  
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
	        cipher.init(Cipher.DECRYPT_MODE, key);  
	        return new String(cipher.doFinal(content.getBytes()));  
		}catch(Exception e){
			throw new CoreException(e);
		}
    } 
	
	/**
	 * 
	* @Title: decryptWithPrivateKey 
	* @Description: RSA私钥解密
	* @Date: 2015年3月8日 下午10:55:39
	* @param content
	* @param privateKey
	* @return
	* @throws CoreException
	 */
	public static String decryptWithPrivateKey(String content, String privateKey) throws CoreException {  
        try{
			// 对密钥解密  
	        byte[] keyBytes = Base64Utils.decoder(privateKey).getBytes();
	        // 取得私钥  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key key = keyFactory.generatePrivate(pkcs8KeySpec);  
	        // 对数据解密  
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
	        cipher.init(Cipher.DECRYPT_MODE, key);  
	        return new String(cipher.doFinal(content.getBytes()));
        }catch(Exception e){
			throw new CoreException(e);
        }
    } 
	
	/**
	 * 私钥签名
	 * @param txt 原字符串
	 * @param privateKey 私钥
	 * @return 签名
	 * @throws CoreException
	 */
	public static byte[] sign(byte[] txt,PrivateKey privateKey) throws CoreException{
		try{
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey);
			signature.update(txt);
			return signature.sign();
		}catch(Exception e){
			throw new CoreException(e);
		}
	}
	
	/**
	 * 公钥验签
	 * @param txt 原字符串
	 * @param sign 签名结果
	 * @param publicKey 公钥
	 * @return 验签结果
	 * @throws CoreException
	 */
	public static boolean unsign(byte[] txt,byte[] sign,PublicKey publicKey) throws CoreException{
		try{
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicKey);
			signature.update(txt);
			return signature.verify(sign);
		}catch(Exception e){
			throw new CoreException(e);
		}
	}
	
	private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";//签名算法

}
