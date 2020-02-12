package com.msacademy.hospital.qx.model.entity;

import org.springframework.http.HttpStatus;

public class Response {
    private HttpStatus httpStatus;
    private String message;
    public Response(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getHttpStatus() {
        return httpStatus.toString();
    }

    public String getMessage() {
        return message;
    }

}