package com.mebay.mapper;

import com.mebay.bean.RequestMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestMethodMapper {
    /**
     * 请求方法
     * 获取全部的菜单所有访问方法
     * 注意它只会返回它的url的id信息
     * @return RequestMethod
     */
    List<RequestMethod> getAll();
}
