package com.clouder.clouderapi.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.service.ResponseService;

@Path("sample")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SampleToken {

    @Autowired
    private ResponseService responseService;

    @Context
    private HttpServletRequest servletRequest;

    @GET
    public Response getUserFromToken(@QueryParam("username") String username) {
        return responseService.getSuccessResponse(username, "User: " + username, Status.OK.getStatusCode());
    }

}
