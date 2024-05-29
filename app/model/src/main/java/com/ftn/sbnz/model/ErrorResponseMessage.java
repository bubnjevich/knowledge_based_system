package com.ftn.sbnz.model;

public class ErrorResponseMessage {

    private String message;

    public ErrorResponseMessage(String message) {
        this.message = message;
    }

    public ErrorResponseMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
