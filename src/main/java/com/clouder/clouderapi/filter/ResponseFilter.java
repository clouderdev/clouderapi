package com.clouder.clouderapi.filter;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.pojo.Constants;
import com.clouder.clouderapi.pojo.ResponseEntity;
import com.clouder.clouderapi.service.KeyGenerationService;
import com.clouder.clouderapi.service.UserService;
import com.clouder.clouderapi.util.JsonUtility;

//@Provider
public class ResponseFilter implements ContainerResponseFilter {

    @Autowired
    private UserService  userService;

    @Autowired
    KeyGenerationService keyGenerationService;

    @Autowired
    JsonUtility          jsonUtility;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");
        UriInfo uriInfo = requestContext.getUriInfo();
        if (!uriInfo.getPath().startsWith("public") && !uriInfo.getPath().endsWith("logout")) {
            String token = requestContext.getHeaderString(Constants.AUTH_HEADER);
            User user = userService.getUserFromToken(token);
            if (user == null) {
                throw new WebApplicationException("User kadya kar raha", Status.UNAUTHORIZED);
            }
            long timestamp = System.currentTimeMillis();
            String combination = user.getUsername() + "@" + timestamp;
            String updatedToken = keyGenerationService.encodeString(combination);
            String responseJson = (String) responseContext.getEntity();
            System.out.println(responseJson);
            ResponseEntity responseEntity = jsonUtility.toObject(responseJson, ResponseEntity.class);
            responseEntity.setToken(updatedToken);
            String response = jsonUtility.toJson(responseEntity);
            responseContext.setEntity(response);
        }
    }

}
