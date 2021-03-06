package com.clouder.clouderapi.filter;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(RequestFilter.class.getName());

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        UriInfo uriInfo = containerRequest.getUriInfo();
        LOGGER.info("ABS:" + uriInfo.getAbsolutePath() + "\nREQ:" + uriInfo.getRequestUri() + "\nBASE:"
                + uriInfo.getBaseUri() + "\nPATH:" + uriInfo.getPath());
        if (!uriInfo.getPath().startsWith("public")) {
            HttpSession session = servletRequest.getSession();
            String username = (String) session.getAttribute("username");
            if (username == null) {
                throw new WebApplicationException("Please login first", Status.UNAUTHORIZED);
            }
            URI uri = UriBuilder.fromUri(uriInfo.getRequestUri()).queryParam("username", username).build();
            containerRequest.setRequestUri(uri);
        }
    }

}
