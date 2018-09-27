package com.mebay.controller;

import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    /**
     * 获取request里经过正确Token解码的Claims属性
     *
     * @param role
     * @param request
     * @return
     * @throws ServletException
     */
    @GetMapping(value = "/api/role/{role}")
    public Boolean login(@PathVariable String role, HttpServletRequest request) throws ServletException {
        Claims claims = (Claims) request.getAttribute("claims");
        if (null == claims) {
            System.out.println("变量为空了！！");
            return false;
        } else {
            System.out.println("恭喜你成功了~");
            return true;
        }
    }

}