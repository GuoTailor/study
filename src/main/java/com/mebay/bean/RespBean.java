package com.mebay.bean;

import com.mebay.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "返回对象",description = "统一返回格式")
public class RespBean<T> {
    @ApiModelProperty(value = "状态码", allowableValues = ""
            + Constant.RESCODE_SUCCESS + ":操作成功, "
            + Constant.NotModified + ":操作失败（已知错误）, "
            + Constant.RESCODE_EXCEPTION_DATA + ":操作失败（未知错误）"
    )
    private String status;
    @ApiModelProperty(value = "对应信息")
    private T message;

    public RespBean() {
    }

    public RespBean(String status, T message) {
        this.status = status;
        this.message = message;
    }

    public RespBean(int status, T message) {
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

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
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
