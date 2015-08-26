package org.rency.common.utils.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.rency.common.utils.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java序列化与反序列化
 * @author rencaiyu
 *
 */
public class SerializerUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SerializerUtils.class);

	/**
	 * 序列化Java对象
	 * @param o Java对象
	 * @param filePath 序列化存放路径
	 * @throws CoreException
	 */
	public static void writeObject(Object o,String filePath) throws CoreException{
		FileOutputStream os = null;
		ObjectOutputStream oos = null;
		try{
			File f=new File(filePath);
		    if(f.exists()){
		    	f.delete();
		    }
		    os =new FileOutputStream(f);
	        //ObjectOutputStream 核心类
		    oos=new ObjectOutputStream(os);
		    oos.writeObject(o);
		    oos.flush();
		    os.flush();
		    logger.info("序列化完成[{}]",filePath);
		}catch(Exception e){
			throw new CoreException(e);
		}finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
				}
			}
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	/**
	 * Java反序列化
	 * @param filePath 反序列化目标地址
	 * @return
	 * @throws CoreException
	 */
	public static Object readObject(String filePath) throws CoreException{
		InputStream is = null;
		ObjectInputStream ois = null;
		try{
			File f=new File(filePath);
		    if(!f.exists()){
		    	return null;
		    }
		    is=new FileInputStream(f);
	        //ObjectOutputStream 核心类
	        ois=new ObjectInputStream(is);
		    Object obj = ois.readObject();
		    logger.info("反序列化完成[{}]",filePath);
		    return obj;
		}catch(Exception e){
			throw new CoreException(e);
		}finally{
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
