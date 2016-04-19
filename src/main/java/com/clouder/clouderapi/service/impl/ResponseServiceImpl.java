package com.clouder.clouderapi.service.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.enums.ResponseType;
import com.clouder.clouderapi.pojo.ResponseEntity;
import com.clouder.clouderapi.pojo.ResponseStatus;
import com.clouder.clouderapi.service.ResponseService;
import com.clouder.clouderapi.util.JsonUtility;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    JsonUtility jsonUtility;

    @Override
    public Response getSuccessResponse(String message, int code) {
        return getSuccessResponse(null, message, code);
    }

    @Override
    public Response getSuccessResponse(Object object, String message, int code) {
        ResponseStatus status = new ResponseStatus(ResponseType.SUCCESS, code, message);
        ResponseEntity responseEntity = new ResponseEntity(object, status);
        String response = jsonUtility.toJson(responseEntity);
        return Response.status(code).entity(response).build();
    }

    @Override
    public Response getErrorResponse(String message, int code) {
        return getErrorResponse(null, message, code);
    }

    @Override
    public Response getErrorResponse(Object object, String message, int code) {
        ResponseStatus status = new ResponseStatus(ResponseType.ERROR, code, message);
        ResponseEntity responseEntity = new ResponseEntity(null, status);
        String response = jsonUtility.toJson(responseEntity);
        return Response.status(code).entity(response).build();
    }

}
