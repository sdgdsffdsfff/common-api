package org.rency.common.utils.tool;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.rency.common.utils.exception.CoreException;
import org.springframework.util.Assert;

public class ConvertUtils {
	
	/**
	 * 将map中的值赋给javaBean
	 * @param map
	 * @param obj
	 * @throws ConvertException
	 */
	public static void map2Bean(Map<String, Object> map,Object obj) throws CoreException{
		try{
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] descriptor = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor property : descriptor){
				String propName = property.getName();
				if(map.containsKey(propName)){
					Object value = map.get(propName);
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		}catch(Exception e){
			throw new CoreException(e);
		}
	}

	/**
	 * @desc 将字符串http参数转换为Map形式
	 * @date 2015年1月9日 下午4:21:16
	 * @param httpParam
	 * @return
	 * @throws CoreException
	 */
	public static Map<String, String> String2Map(String httpParam) throws CoreException{
		Map<String, String> map = new HashMap<String, String>();
		try{
			Assert.notNull(httpParam, "转换字符串不能为空.");
			String[] sections = httpParam.split("&");
			for(String section : sections){
				String[] keyValue = section.split("=");
				map.put(keyValue[0], keyValue[1]);
			}
		}catch(Exception e){
			throw new CoreException(e);
		}
		return map;
	}
	
	/**
	 * @desc 将Map转换为http字符串参数形式
	 * @date 2015年1月9日 下午4:37:09
	 * @param httpParamMap
	 * @return
	 * @throws CoreException
	 */
	public static String Map2String(Map<String, String> httpParamMap) throws CoreException{
		String httpParam = "";
		try{
			Assert.isTrue(httpParamMap.isEmpty(), "转换集合不能为空.");
			for(String key : httpParamMap.keySet()){
				httpParam += key;
				httpParam += "=";
				httpParam += httpParamMap.get(key);
				httpParam += "&";
			}
			httpParam = httpParam.substring(0, httpParam.lastIndexOf("&"));
		}catch(Exception e){
			throw new CoreException(e);
		}
		return httpParam.trim();
	}
	
}
