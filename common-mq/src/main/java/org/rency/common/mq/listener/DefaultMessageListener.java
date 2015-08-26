package org.rency.common.mq.listener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.rency.common.mq.service.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @desc 消息接受监听器
 * @author T-rency
 * @date 2014年12月23日 上午10:50:49
 */
@Component
public class DefaultMessageListener implements MessageListener,InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultMessageListener.class);
	
	@Resource(name="messageHandler")
	private MessageHandler messageHandler;
	
	@Override
	public void onMessage(Message message) {
		/************* 判断消息类型  *************/
		Object request = null;
		try{
			if(message instanceof ObjectMessage){
				ObjectMessage objMessage = (ObjectMessage) message;
				request = objMessage.getObject();
			}else if(message instanceof TextMessage){
				TextMessage txtMessage = (TextMessage) message;
				request = txtMessage.getText();
			}else if(message instanceof ActiveMQBytesMessage){
				ActiveMQBytesMessage byteMessage = (ActiveMQBytesMessage) message;
				request = new String(byteMessage.getContent().data);
			}else{
				logger.error("MQ消息接收异常, 未知的消息类型. Message:"+message);
				throw new RuntimeException("MQ消息接收异常, 未知的消息类型. Message:"+message);
			}
		}catch(JMSException e){
			logger.error("MQ接收消息处理异常, 接收消息:{}.",message, e);
			return;
		}
		
		logger.info("Listen on message[{}]",request);
		messageHandler.handler(request);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(messageHandler, "请实现'MessageHandler'消息处理器.");
	}

}
