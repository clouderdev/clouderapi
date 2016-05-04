package com.clouder.clouderapi.pojo;

import com.clouder.clouderapi.enums.ResponseType;

public class ResponseStatus {
    ResponseType type;
    int          code;
    String       message;

    public ResponseStatus() {
    }

    public ResponseStatus(ResponseType type, int code, String message) {
        super();
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseStatus [type=" + type + ", code=" + code + ", message=" + message + "]";
    }

}
