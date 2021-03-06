package com.clouder.clouderapi.api;

import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.service.ResponseService;
import com.clouder.clouderapi.service.UserService;

@Path("public/signup")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserSignupApi {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    @POST
    public Response signup(String json) {
        User user = userService.saveUser(json);
        if (user != null) {
            return responseService.getSuccessResponse("User created", Status.CREATED.getStatusCode());
        } else {
            return responseService.getErrorResponse("Unable to create user",
                    Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

    @POST
    @Path("password")
    public Response savePassword(String json) throws MessagingException {
        User user = userService.savePassword(json);
        if (user != null) {
            return responseService.getSuccessResponse("Password saved", Status.OK.getStatusCode());
        } else {
            return responseService.getErrorResponse("Unable to save password",
                    Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

    @GET
    @Path("verifyemail")
    public Response verifyUser(@QueryParam("username") String username, @QueryParam("key") String key) {
        boolean isVerified = userService.verifyUser(username, key);
        return responseService.getSuccessResponse(isVerified, "User verfied", Status.OK.getStatusCode());
    }

}
