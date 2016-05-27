package com.clouder.clouderapi.exception.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.service.ResponseService;

@Provider
public class ClouderExceptionMapper implements ExceptionMapper<ClouderException> {

    @Autowired
    private ResponseService responseService;

    @Override
    public Response toResponse(ClouderException exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return responseService.getErrorResponse(writer.toString(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

}
