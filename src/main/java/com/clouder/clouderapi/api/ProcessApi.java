package com.clouder.clouderapi.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.service.ProcessService;

@Path("process")
@Component
public class ProcessApi {

	@Autowired
	private ProcessService processService;

	@GET
	public Response process() {
		processService.saveUser();
		return Response.ok().entity("{\"message\" : \"All iz well\"}").build();
	}
}
