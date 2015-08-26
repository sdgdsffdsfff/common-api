package org.rency.common.cxf.restful.facade;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@WebService(targetNamespace="http://facade.restful.cxf.rency.com")
public interface TestFacade {

	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("welcome/{name}")
	public String welcome(@PathParam("name")String name);
	
}