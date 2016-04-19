package com.clouder.clouderapi.service;

import javax.ws.rs.core.Response;

public interface ResponseService {
    Response getSuccessResponse(String message, int code);

    Response getSuccessResponse(Object object, String message, int code);

    Response getErrorResponse(String message, int code);

    Response getErrorResponse(Object object, String message, int code);
}
