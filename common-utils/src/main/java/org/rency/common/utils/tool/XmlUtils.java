package org.rency.common.utils.tool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rency.common.utils.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xml工具类
 * @author rencaiyu
 *
 */
public class XmlUtils {

	private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);
	
	private static ConcurrentHashMap<String, Map<String,String>> cache = new ConcurrentHashMap<String, Map<String,String>>();
	
	/**
	 * 读取xml文件节点
	 * @param xmlPath xml文件路径
	 * @param nodeName 读取节点名称
	 * @param isCache 是否只用缓存
	 * @return
	 * @throws CoreException
	 */
	public static Map<String, String> getNodes(String xmlPath,String nodeName,boolean isCache) throws CoreException{
		String cacheKey = xmlPath+"/"+nodeName;
		if(cache.containsKey(cacheKey) && isCache){
			return cache.get(cacheKey);
		}
		logger.info("读取[{}]文件中节点[{}]",xmlPath,nodeName);
		Map<String, String> nodes = new HashMap<String, String>();
		try {
			File xmlFile = new File(xmlPath);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName(nodeName);
			for(int i=0;i< nodeList.getLength();i++){
				Node node = nodeList.item(i);
				Element elem = (Element) node;
				nodes.put(elem.getAttribute("name"),elem.getAttribute("value"));
			}
			if(isCache){
				cache.put(cacheKey, nodes);
			}
		} catch (Exception e) {
			logger.error("读取文件[{}]异常.",xmlPath,e);
			throw new CoreException(e);
		}
		return nodes;
	}
}
