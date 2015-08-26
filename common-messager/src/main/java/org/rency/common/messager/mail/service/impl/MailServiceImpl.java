package org.rency.common.messager.mail.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.rency.common.messager.exception.MailException;
import org.rency.common.messager.mail.beans.EmailEntity;
import org.rency.common.messager.mail.service.MailService;
import org.rency.common.utils.domain.SYSDICT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceImpl implements MailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	private JavaMailSender mailSender;

	@Override
	public boolean send(EmailEntity entity) throws MailException{
		logger.info("开始发送邮件:"+entity.toString());
		try{
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMsg,false,SYSDICT.CHARSET);
			
			String[] tos = new String[entity.getTo().size()];
			for(int i=0;i<entity.getTo().size();i++){
				tos[i] = entity.getTo().get(i);
			}
			messageHelper.setTo(tos);
			
			String[] ccs = new String[entity.getCc().size()];
			for(int i=0;i<entity.getCc().size();i++){
				ccs[i] = entity.getCc().get(i);
			}
			messageHelper.setCc(ccs);
			
			messageHelper.setFrom(entity.getFrom());
			messageHelper.setSentDate(new Date());
			messageHelper.setSubject(entity.getSubject());
			messageHelper.setText(entity.getContent(),entity.isHtml());
			mailSender.send(mimeMsg);
			return true;
		}catch(Exception e){
			logger.error("发送邮件异常.{}",entity.toString(),e);
			throw new MailException(e);
		}finally{
			logger.info("发送邮件结束:"+entity.toString());
		}
	}

	@Override
	public boolean send(String from, String target, String subject,String content, boolean isHtml) throws MailException{
		EmailEntity email = new EmailEntity();
		email.setHtml(isHtml);
		email.setFrom(from);
		List<String> to = new ArrayList<String>();
		to.add(target);
		email.setTo(to);
		email.setSubject(subject);
		email.setContent(content);
		return send(email);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
