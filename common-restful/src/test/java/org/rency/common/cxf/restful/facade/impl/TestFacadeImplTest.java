package org.rency.common.cxf.restful.facade.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rency.common.cxf.restful.facade.TestFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestFacadeImplTest {

	private TestFacade testFacade;
	@Ignore
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/resources/applicationContext.xml");
		testFacade = ctx.getBean("testWsClient",TestFacade.class);
	}
	
	@Ignore
	@Test
	public void testWelcome() {
		System.out.println("RESTful WebService Test:"+testFacade.welcome("rency"));
	}

}
