package org.rency.common.mq.support;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageConvert {

	public static Message toMessage(Session session,Object request) throws JMSException{
		if (request instanceof Message) {
			return (Message) request;
		} else if (request instanceof String) {
			return createTextMessage(session, (String)request);
		}  else if (request instanceof byte[]) {
			return createByteArrayMessage((byte[]) request, session);
		} else if (request instanceof Map) {
			return createMapMessage(session, (Map<?, ?>)request);
		} else  if (request instanceof Serializable) {
			return createObjectMessage(session, (Serializable)request);
		} else {
			throw new IllegalArgumentException("Invalid message type. Message support object, json and map.");
		}
	}
	
	public static Object fromMessage(Session session,Message message) throws JMSException{
		if(message instanceof Object){
			return (Object) message;
		}else if (message instanceof TextMessage) {
			return extractTextFromMessage((TextMessage) message);
		}else if (message instanceof BytesMessage) {
			return extractByteArrayFromMessage((BytesMessage) message);
		}else if (message instanceof MapMessage) {
			return extractMapFromMessage((MapMessage) message);
		}else if (message instanceof ObjectMessage) {
			return extractSerializableFromMessage((ObjectMessage) message);
		}else {
			return message;
		}
	}
	
	private static Message createTextMessage(Session session, String text)throws JMSException {
		return session.createTextMessage(text);
	}
	
	private static Message createByteArrayMessage(byte[] bytes, Session session) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(bytes);
		return message;
	}

	private static Message createMapMessage(Session session, Map<?, ?> map)throws JMSException {
		MapMessage message = session.createMapMessage();		
		for (Object key : map.keySet()) {			
			if (!(key instanceof String)) {
				throw new IllegalArgumentException("Cannot convert non-String key of type [" +key.getClass().getName() + "] to JMS MapMessage entry");
			}			
			message.setObject((String) key, map.get(key));
		}		
		return message;
	}

	private static Message createObjectMessage(Session session, Serializable object)throws JMSException {
		return session.createObjectMessage(object);
	}
	
	private static String extractTextFromMessage(TextMessage message) throws JMSException {
		return message.getText();
	}

	private static byte[] extractByteArrayFromMessage(BytesMessage message) throws JMSException {
		byte[] bytes = new byte[(int) message.getBodyLength()];
		message.readBytes(bytes);
		return bytes;
	}

	private static Map<String, Object> extractMapFromMessage(MapMessage message) throws JMSException {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> en = message.getMapNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			map.put(key, message.getObject(key));
		}
		return map;
	}
	
	private static Serializable extractSerializableFromMessage(ObjectMessage message) throws JMSException {
		return message.getObject();
	}
}