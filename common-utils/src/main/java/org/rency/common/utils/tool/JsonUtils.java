package org.rency.common.utils.tool;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.rency.common.utils.exception.CoreException;

import com.alibaba.fastjson.JSON;

/**
 * JSON工具类
 * @author rencaiyu
 *
 */
public class JsonUtils {
	
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * json转换为java对象
	 * @param json
	 * @param clazz
	 * @return
	 * @throws ConvertException
	 */
	public static <T> T json2Object(String json,Class<T> clazz)throws CoreException{
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new CoreException(e);
		} catch (JsonMappingException e) {
			throw new CoreException(e);
		} catch (IOException e) {
			throw new CoreException(e);
		}
	}
	
	/**
	 * 将java对象转换为json
	 * @param object
	 * @return
	 * @throws ConvertException
	 */
	public static String object2Json(Object object)throws CoreException{
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonParseException e) {
			throw new CoreException(e);
		} catch (JsonMappingException e) {
			throw new CoreException(e);
		} catch (IOException e) {
			throw new CoreException(e);
		}
	}

	/**
	 * 将对象转换为json
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object){
		return JSON.toJSONString(object);
	}
	
}
