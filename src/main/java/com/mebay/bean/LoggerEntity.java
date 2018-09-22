package com.mebay.bean;

public class LoggerEntity {
    private Long id;            //编号
    private String ip;          //ip地址
    private String url;         //请求路径
    private String type;        //普通请求，ajax请求
    private String method;      //请求方式（get post）
    private String paramData;   //请求参数
    private String token;       //请求的token
    private int time;           //请求时间
    private String returnTime;  //接口返回时间
    private String returnData;  //接口返回数据
    private String statusCode;  //错误码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "LoggerEntity{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", method='" + method + '\'' +
                ", paramData='" + paramData + '\'' +
                ", token='" + token + '\'' +
                ", time=" + time +
                ", returnTime='" + returnTime + '\'' +
                ", returnData='" + returnData + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
