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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.pojo.Constants;
import com.clouder.clouderapi.service.UserService;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {
    
    private static final Logger LOGGER = LogManager.getLogger(RequestFilter.class.getName());

    @Autowired
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        UriInfo uriInfo = containerRequest.getUriInfo();
        LOGGER.info("ABS:" + uriInfo.getAbsolutePath() + "\nREQ:" + uriInfo.getRequestUri() + "\nBASE:"
                + uriInfo.getBaseUri() + "\nPATH:" + uriInfo.getPath());
        if (!uriInfo.getPath().startsWith("public")) {
            String token = containerRequest.getHeaderString(Constants.AUTH_HEADER);
            LOGGER.info(token);
            User user = userService.getUserFromToken(token);
            if (user == null) {
                throw new WebApplicationException("User kadya kar raha", Status.UNAUTHORIZED);
            }
            URI uri = UriBuilder.fromUri(uriInfo.getRequestUri()).queryParam("username", user.getUsername()).build();
            containerRequest.setRequestUri(uri);
        }
    }

}
