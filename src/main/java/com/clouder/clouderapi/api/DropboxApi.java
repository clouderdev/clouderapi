package com.clouder.clouderapi.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.ResponseService;
import com.dropbox.core.v2.files.Metadata;

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
    @Path("file")
    public Response listFiles(@QueryParam("username") String username, @QueryParam("cloudId") String cloudId,
            @QueryParam("path") String parentDir) {
        List<Metadata> listFiles = dropboxService.listFiles(username, cloudId, parentDir);
        return responseService.getSuccessResponse(listFiles, "List of files", 200);
    }

    @DELETE
    @Path("file")
    public Response deleteFile(@QueryParam("username") String username, @QueryParam("cloudId") String cloudId,
            @QueryParam("path") String filePath) {
        Metadata metadata = dropboxService.deleteFile(username, cloudId, filePath);
        return responseService.getSuccessResponse(metadata, "File deleted successfully", 200);
    }

    @GET
    @Path("file/download")
    public Response downloadFile(@QueryParam("username") String username, @QueryParam("cloudId") String cloudId,
            @QueryParam("path") String filePath) {
        InputStream inputStream = dropboxService.downloadFile(username, cloudId, filePath);
        return Response.ok(inputStream).header("content-disposition", "attachment; filename=\"" + "file.xml" + "\"")
                .build();
    }

    @POST
    @Path("file/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@QueryParam("username") String username, @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails, @QueryParam("cloudId") String cloudId,
            @QueryParam("path") String filePath) throws IOException {
        dropboxService.uploadFile(username, cloudId, filePath, inputStream, fileDetails);
        return Response.ok("{\"message\":\"Uploaded\"}").build();
    }

}
