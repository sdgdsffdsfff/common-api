package org.rency.common.messager.mail.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rency.common.messager.exception.MessageException;
import org.rency.common.messager.mail.beans.EmailEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MainServiceTest {
	
	private MailService mailService;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/resources/applicationContext.xml");
		mailService = ctx.getBean(MailService.class);
	}

	@Ignore
	@Test
	public void testSendMailEmailEntity() throws MessageException {
		EmailEntity email = new EmailEntity();
		email.setFrom("user_rcy@163.com");
		List<String> to = new ArrayList<String>();
		to.add("yu7715261@126.com");
		email.setTo(to);
		email.setSubject("test");
		email.setContent("test 2015-06-05 by rency");
		boolean sendResult = mailService.send(email);
		System.out.println("test send email result:"+sendResult);
	}

}
