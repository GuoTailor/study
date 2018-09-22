package com.study.bean;

public class RespBean {
    private String status;
    private Object message;

    public RespBean() {
    }

    public RespBean(String status, Object message) {
        this.status = status;
        this.message = message;
    }

    public RespBean(int status, Object message) {
        this.status = Integer.toString(status);
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = Integer.toString(status);
    }

    public Object getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\": \"" + status + '\"' +
                ", \"message\": \"" + message +
                "\"}";
    }
}
