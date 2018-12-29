package com.mebay.bean;

import com.mebay.filter.LogAspect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 * Created by gyh on 2018/10/25.
 * 统一放回
 */
@ApiModel(value = "返回对象",description = "统一返回格式")
public class RespBody<T> {
    private Map<Integer, String> parameters = new TreeMap<>();
    @ApiModelProperty(value = "状态码", allowableValues = "1:操作成功, 0:操作失败")
    private int code;
    private BiConsumer<Integer, T> function;
    private T data;

    public RespBody() {

    }

    public RespBody(int code){
        this.code = code;
    }

    public RespBody(T data) {
        this.data = data;
    }

    public RespBody(int code, String msg){
        this.code = code;
        parameters.put(code, msg);
    }

    public RespBody(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public RespBody(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        parameters.put(code, msg);
    }

    /**
     * 后续操作
     * 注意，该方法不会立即执行。只有在用 json 序列化时或调用{@link #getMsg()} 方法才会调用
     * @param function 要做的操作
     */
    public RespBody<T> processing(BiConsumer<Integer, T> function) {
        this.function = function;
        return this;
    }

    public RespBody<T> put(int code, String msg) {
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
    public String getMsg() {
        LogAspect.threadLocal.set(code > 0 ? "成功" : (String)parameters.get(code));
        if (function != null) function.accept(code, data);
        return parameters.get(code);
    }

    public T getData() {
        return data;
    }

    public RespBody<T> setData(T data) {
        this.data = data;
        return this;
    }
}
