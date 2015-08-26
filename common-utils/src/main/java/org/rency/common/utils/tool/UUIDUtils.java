package org.rency.common.utils.tool;

import java.util.UUID;

public class UUIDUtils {

	/**
	 * 生成UUID(无 "-" 符号)
	* @Title: genetator 
	* @Description: TODO
	* @Date: 2015年6月4日 下午9:44:24
	* @return
	 */
	public static String genetator(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "").toUpperCase();
	}
	
}