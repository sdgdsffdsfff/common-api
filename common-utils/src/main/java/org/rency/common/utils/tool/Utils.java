package org.rency.common.utils.tool;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.rency.common.utils.exception.CoreException;

public class Utils {
	
	/**
	 * @desc 生成序列号
	 * @date 2014年11月11日 下午3:52:44
	 * @param length 主键长度(建议长度25位)
	 * @return
	 * @throws CoreException
	 */
	public synchronized static String generateId(int length){
		StringBuilder builder = new StringBuilder();
		String dateTime = new SimpleDateFormat("yyyyMMddHHmmddSSS").format(new Date());//17
		String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		builder.append(pid);
		builder.append(dateTime);
		if(builder.length() > length){
			builder.append(builder.subSequence(0, length));
		}else{
			int offset = length - builder.length();
			int ran = generateInteger(offset);
			builder.append(ran);
		}
		
		return builder.toString();
	}
		
	public static String parseLastModified(String lastModified) throws CoreException{
		try {
			if(StringUtils.isBlank(lastModified)){
				return DateUtils.getNowDateTime();
			}
		    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);  
		    Date date = sdf.parse(lastModified);
		    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(date);
		} catch (ParseException e) {
			return DateUtils.getNowDateTime();
		}
	}
	
	/**
	 * @desc 生成随机数
	 * @date 2014年8月18日 上午10:21:17
	 * @param 随机数长度
	 * @return
	 * @throws CoreException
	 */
	public synchronized static int generateInteger(int length){
		StringBuilder sbBuilder = new StringBuilder();
		for(int i=0;i<length;i++){
			int ran = (int)(Math.random()*10);
			if(ran == 0){
				i--;
				continue;
			}
			sbBuilder.append(ran);
		}
		return Integer.parseInt(sbBuilder.toString());
	} 
	
	/**
	 * @desc 生成随机字符串
	 * @date 2014年8月18日 上午10:20:07
	 * @param 生成字符串长度
	 * @return
	 * @throws CoreException
	 */
	public static String generateString(int length){
		int uuidLength = 32;
		int count = 0;
		if(length >=uuidLength){
			count = (length % uuidLength) == 0 ? 1: (length / uuidLength) +1;
		}else{
			count = (uuidLength % length) == 0 ? 1: (uuidLength / length) +1;
		}
		StringBuilder sbBuilder = new StringBuilder();
		for(int i=0;i<count;i++){
			sbBuilder.append(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		String result = sbBuilder.substring(0,length).toUpperCase();
		return result;
	} 
	
	/**
	 * @desc 根据类、方法名获取方法实体
	 * @date 2015年2月5日 下午12:31:40
	 * @param actionClass
	 * @param methodName
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getActionMethod(Class actionClass, String methodName)throws CoreException {
		Method method;
		try {
			method = actionClass.getMethod(methodName, new Class[0]);
		} catch (NoSuchMethodException e) {
			throw new CoreException(e);
		}
		return method;
	}
	
	/**
	 * 线程休眠
	 * @param sleep(秒)
	 */
	public static void sleep(long sleep){
		try {
			Thread.sleep(sleep * 1000);
		} catch (InterruptedException e) {
		}
	}
	
}