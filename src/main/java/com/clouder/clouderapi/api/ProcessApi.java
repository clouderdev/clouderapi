package com.clouder.clouderapi.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.service.ProcessService;

@Path("process")
@Component
public class ProcessApi {

	@Autowired
	private ProcessService processService;

	@GET
	@Path("save")
	public Response save() {
		processService.saveUser();
		return Response.ok().entity("{\"message\" : \"All is well\"}").build();
	}

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() throws JsonGenerationException, JsonMappingException, IOException {
		List<User> users = processService.getUsers();
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(users);
		return Response.ok().entity(json).build();
	}

}
