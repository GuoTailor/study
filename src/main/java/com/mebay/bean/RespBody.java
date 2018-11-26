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
    private Map<Integer, T> parameters = new TreeMap<>();
    @ApiModelProperty(value = "状态码", allowableValues = "1:操作成功, 0:操作失败")
    private int code;
    private BiConsumer<Integer, T> function;

    public RespBody() {

    }

    public RespBody(int code){
        this.code = code;
    }

    /**
     * 后续操作
     * 注意，该方法不会立即执行。只有在用 json 序列化时或调用{@link #getMsg()} 方法才会调用
     * @param function 要做的操作
     */
    public void processing(BiConsumer<Integer, T> function) {
        this.function = function;
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
        LogAspect.threadLocal.set(code > 0 ? "成功" : (String)parameters.get(code));
        if (function != null) function.accept(code, parameters.get(code));
        return parameters.get(code);
    }
}
