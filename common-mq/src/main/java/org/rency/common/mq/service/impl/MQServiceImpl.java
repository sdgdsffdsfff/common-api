package org.rency.common.mq.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.rency.common.mq.beans.MQRequest;
import org.rency.common.mq.exception.MQException;
import org.rency.common.mq.service.MQService;
import org.rency.common.mq.support.MessageConvert;
import org.rency.common.utils.exception.CoreException;
import org.rency.common.utils.tool.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MQServiceImpl implements MQService{
	
	private static final Logger logger = LoggerFactory.getLogger(MQServiceImpl.class);

	private JmsTemplate jmsTemplate;
	
	@Override
	public boolean send(final MQRequest request) throws MQException {
		try{
			jmsTemplate.setDeliveryPersistent(request.isDeliveryPersistent());
			jmsTemplate.send(request.getDestinationName(),new MessageCreator(){
				@Override
				public Message createMessage(Session session)throws JMSException {
					try {
						Message message = MessageConvert.toMessage(session, JsonUtils.object2Json(request.getContent()));
						return message;
					} catch (JMSException e) {
						logger.error("发送MQ消息转换时发生异常, 转换内容:{}.",request.getContent());
						throw e;
					}catch (CoreException e) {
						logger.error("发送MQ消息转换时发生异常, 转换内容:{}.",request.getContent());
						throw new JMSException(e.getErrorMessage());
					}
				}});
			return true;
		}catch(Exception e){
			logger.error("发送MQ消息异常, 原始请求:{}.",request,e);
			throw new MQException(e);
		}
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
