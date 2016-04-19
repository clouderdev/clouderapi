package com.clouder.clouderapi.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.clouder.clouderapi.service.ResponseService;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Autowired
    private ResponseService responseService;

    @Override
    public Response toResponse(WebApplicationException exception) {
        String message = exception.getMessage();
        int code = exception.getResponse().getStatus();
        return responseService.getErrorResponse(message, code);
    }

}
