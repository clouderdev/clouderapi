package com.clouder.clouderapi.api;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.ResponseService;

@Path("dropbox")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DropboxApi {

    @Autowired
    private ResponseService responseService;

    @Context
    private HttpServletRequest servletRequest;

    @Autowired
    @Qualifier("dropbox")
    CloudService dropboxService;

    @GET
    @Path("add")
    public Response addDropbox(@QueryParam("username") String username) {
        Cloud cloud = dropboxService.addCloud(servletRequest, username);
        return responseService.getSuccessResponse(cloud, "Cloud added successfully", 200);
    }

    @GET
    @Path("authenticate")
    public Response authenticateDropbox(@QueryParam("username") String username) {
        URI uri = dropboxService.authenticateCloud(servletRequest, username);
        return Response.temporaryRedirect(uri).build();
    }

    @GET
    @Path("files")
    public Response listFiles(@QueryParam("username") String username, @QueryParam("cloudId") String cloudId,
            @QueryParam("parent") String parentDir) {
        dropboxService.listFiles(servletRequest, username);
        return responseService.getSuccessResponse(cloudId, "List of files", 200);
    }

}
