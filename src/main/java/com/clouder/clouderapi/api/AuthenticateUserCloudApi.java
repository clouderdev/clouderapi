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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.ResponseService;
import com.clouder.clouderapi.service.UserService;

@Path("auth/cloud")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticateUserCloudApi {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    @Context
    private HttpServletRequest servletRequest;

    @Autowired
    @Qualifier("dropbox")
    CloudService dropboxService;

    @GET
    @Path("onedrive")
    public Response authenticateOneDrive(@QueryParam("code") String token) {
        User user = userService.getUserFromToken(token);
        return responseService.getSuccessResponse(user, "User with token = " + token, Status.OK.getStatusCode());
    }

    @GET
    @Path("dropbox")
    public Response authenticateDropbox(@QueryParam("username") String username) {
        dropboxService.addCloud(servletRequest, username);
        return responseService.getSuccessResponse(username, 200);
    }

}
