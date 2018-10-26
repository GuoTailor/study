package com.mebay.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 * Created by gyh on 2018/10/25.
 */
@ApiModel(value = "返回对象",description = "统一返回格式")
public class RespBody<T> {
    private Map<Integer, T> parameters = new TreeMap<>();
    @ApiModelProperty(value = "状态码", allowableValues = ""
            + "1" + ":操作成功, "
            + "0" + ":操作失败"
    )
    private int code;

    public RespBody() {

    }

    public RespBody(int code){
        this.code = code;
    }

    public void processing(BiConsumer<Integer, T> function) {
        function.accept(code, parameters.get(code));
    }

    public RespBody(int code, T msg){
        this.code = code;
        parameters.put(code, msg);
    }

    public RespBody<T> put(int code, T msg) {
        parameters.put(code, msg);
        return this;
    }

    public int getCode() {
        return code > 0 ? 1 : 0;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @ApiModelProperty(value = "对应信息")
    public T getMsg() {
        return parameters.get(code);
    }
}
