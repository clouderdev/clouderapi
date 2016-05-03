package com.clouder.clouderapi.pojo;

public class ResponseEntity {
    private Object         response;
    private ResponseStatus status;
    private String token;

    public ResponseEntity(Object response, ResponseStatus status) {
        super();
        this.response = response;
        this.status = status;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResponseEntity [response=" + response + ", status=" + status + ", token=" + token + "]";
    }

}
