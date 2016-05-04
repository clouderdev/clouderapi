package com.clouder.clouderapi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.service.ResponseService;

@Path("logout")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserLogoutApi {

    @Autowired
    private ResponseService responseService;

    @GET
    public Response logout() {

        return responseService.getSuccessResponse("User logged out", Status.OK.getStatusCode());
    }
}
