package com.mebay.common;

import com.mebay.bean.StreamTree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Util {
    public static <T> boolean hasAny(List<T> gather, T... element) {
        for (T t : gather) {
            for (T e : element) {
                if (t.equals(e))
                    return true;
            }
        }
        return false;
    }

    /**
     * 用自己的方法判断{@code} element 是否在{@code} gather 里面出现过<p>
     * 该方法不支持对继承了{@link com.mebay.bean.StreamTree}方法的 Children 遍历
     *
     * @param fun     判断方法
     * @param gather  目标集合
     * @param element 要检查的集合
     * @param <T>     目标对象
     * @param <K>     检查对象
     * @return 如果 {@code} element 中的任何一个元素在{@code} gather 里面出现过就返回true 否则返回false
     */
    public static <T, K> boolean hasAny(BiFunction<T, K, Boolean> fun, List<T> gather, K... element) {
        for (T t : gather) {
            for (K k : element) {
                if (fun.apply(t, k))
                    return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Object obj) {
        Class c = obj.getClass();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object o = null;
            try {
                o = new PropertyDescriptor(field.getName(), c).getReadMethod().invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
                e.printStackTrace();
            }
            if (o instanceof String) {
                if (!((String) o).isEmpty()) {
                    System.out.println(o);
                    return false;
                } else
                    continue;
            }
            if (o instanceof Collection) {
                if (!((Collection) o).isEmpty())
                    return false;
                else
                    continue;
            }
            if (o != null)
                return false;
        }
        return true;
    }

    public static void addHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        System.out.println(UserUtils.getIpAddr(httpServletRequest) + "   " + httpServletRequest.getHeader("Origin"));
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE ,PUT");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since,"
                + " Pragma, Last-Modified, Cache-Control, Expires, Content-Type, "
                + "X-E4M-With,userId,token,Authorization,deviceId,Access-Control-Allow-Origin,Access-Control-Allow-Headers,Access-Control-Allow-Methods");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        httpServletResponse.setHeader("XDomainRequestAllowed", "1");
    }

}
