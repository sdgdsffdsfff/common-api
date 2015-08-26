package org.rency.common.cxf.restful.facade.impl;

import javax.jws.WebService;

import org.rency.common.cxf.restful.facade.TestFacade;
import org.springframework.stereotype.Component;

@Component
@WebService(endpointInterface="com.rency.cxf.restful.facade.TestService")
public class TestFacadeImpl implements TestFacade {

	@Override
	public String welcome(String name) {
		return "Welcome to "+name+"!";
	}

}
