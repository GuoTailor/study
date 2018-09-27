package com.mebay.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.bean.LoggerEntity;
import com.mebay.common.UserUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

public class LogAspect implements HandlerInterceptor {

    private static String LOGGER_SEND_TIME = "_send_time";
    private static String LOGGER_ENTITY = "_logger_entity";
    private static String LOGGER_RETURN = "_return_data";
    private SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        //创建日志实体
        LoggerEntity logger = new LoggerEntity();
        //获取请求token
        String sessionId = request.getHeader("Authorization");
        //请求路径
        String url = request.getRequestURI();
        //获取请求参数信息
        String paramData = new ObjectMapper().writeValueAsString(request.getParameterMap());
        logger.setIp(UserUtils.getIpAddr(request));
        //设置请求方法
        logger.setMethod(request.getMethod());
        //设置请求类型( json|普通请求)
        logger.setType(UserUtils.getRequestType(request));
        //设置请求参数内容ison字符串
        logger.setParamData(paramData);
        logger.setUrl(url);
        logger.setToken(sessionId);
        logger.setId(UserUtils.getCurrentUser().getId());
        request.setAttribute(LOGGER_SEND_TIME, System.currentTimeMillis());
        request.setAttribute(LOGGER_ENTITY, logger);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        int status = response.getStatus();
        long currentTime = System.currentTimeMillis();
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        LoggerEntity loggerEntity = (LoggerEntity) request.getAttribute(LOGGER_ENTITY);
        loggerEntity.setTime((int) (currentTime - time));
        loggerEntity.setReturnTime(format.format(currentTime));
        loggerEntity.setStatusCode(Integer.toString(status));
        loggerEntity.setReturnData(new ObjectMapper().writeValueAsString(request.getAttribute(LOGGER_RETURN)));
        System.out.println(loggerEntity);
    }

}
