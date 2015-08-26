package org.rency.common.mq.beans;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.rency.common.mq.enums.MessageLevel;

/**
 * MQ消息请求
 * @author rencaiyu
 *
 */
public class MQRequest {

	/**
	 * 消息级别
	 */
	private MessageLevel messageLevel = MessageLevel.INFO;
	
	/**
	 * 目的地
	 */
	private String destinationName;
	
	/**
	 * 消息来源
	 */
	private String fromUser;
	
	/**
	 * 消息内容
	 */
	private Object content;
	
	/**
	 * 是否持久化
	 */
	private boolean deliveryPersistent;
	
	/**
	 * 消息创建时间
	 */
	private Date createTime = new Date();

	public MessageLevel getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(MessageLevel messageLevel) {
		this.messageLevel = messageLevel;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public boolean isDeliveryPersistent() {
		return deliveryPersistent;
	}

	public void setDeliveryPersistent(boolean deliveryPersistent) {
		this.deliveryPersistent = deliveryPersistent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}