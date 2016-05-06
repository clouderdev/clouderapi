package com.clouder.clouderapi.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.service.ResponseService;
import com.clouder.clouderapi.service.UserService;

@Path("public/login")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserLoginApi {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    @Context
    private HttpServletRequest servletRequest;

    @POST
    public Response login(String json) {
        User user = userService.login(json);
        if (user != null) {
            HttpSession session = servletRequest.getSession();
            session.setAttribute("username", user.getUsername());
            return responseService.getSuccessResponse(user.getUsername(), "Login Successful", 200);
        }
        return responseService.getErrorResponse("Login Failed", 401);
    }

}
