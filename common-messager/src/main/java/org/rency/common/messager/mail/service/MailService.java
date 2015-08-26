package org.rency.common.messager.mail.service;

import org.rency.common.messager.exception.MailException;
import org.rency.common.messager.mail.beans.EmailEntity;

public interface MailService {

	/**
	 * 发送邮件
	 * @param entity 邮件主体
	 * @return
	 * @throws MailException
	 */
	public boolean send(EmailEntity entity) throws MailException;
	
	/**
	 * 发送邮件
	 * @param from 发件人
	 * @param target 收件人
	 * @param subject 主题
	 * @param content 内容
	 * @param isHtml 是否html格式发送
	 * @return
	 * @throws MailException
	 */
	public boolean send(String from,String target,String subject,String content,boolean isHtml) throws MailException;
	
}