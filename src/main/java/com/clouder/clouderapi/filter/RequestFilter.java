package com.clouder.clouderapi.filter;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.pojo.Constants;
import com.clouder.clouderapi.service.UserService;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        UriInfo uriInfo = containerRequest.getUriInfo();
        String token = containerRequest.getHeaderString(Constants.AUTH_HEADER);
        System.out.println(token);
        User user = userService.getUserFromToken(token);
        if (user == null) {
            throw new WebApplicationException("User kadya kar raha", Status.UNAUTHORIZED);
        }
        URI uri = UriBuilder.fromUri(uriInfo.getRequestUri()).queryParam("token", token).build();
        containerRequest.setRequestUri(uri);
    }

}
