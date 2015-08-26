package org.rency.common.mq.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rency.common.mq.beans.MQRequest;
import org.rency.common.mq.enums.MessageLevel;
import org.rency.common.mq.exception.MQException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MQServiceTest {
	
	private MQService mqService;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/resources/applicationContext.xml");
		mqService = ctx.getBean(MQService.class);
	}

	@Ignore
	@Test
	public void testSend() throws MQException {
		MQRequest request = new MQRequest();
		request.setContent("This is ActiveMQ test message.");
		request.setDeliveryPersistent(true);
		request.setDestinationName("common.test");
		request.setMessageLevel(MessageLevel.INFO);
		request.setFromUser("common-test");
		mqService.send(request);
	}

}
