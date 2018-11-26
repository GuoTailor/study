package com.mebay.common;

import com.mebay.bean.StreamTree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public class Util {
    /**
     * 移除两个列表中所有相同的元素
     *
     * @param c1  一个列表
     * @param c2  另一个列表
     * @param <T> 元素的类型
     */
    public static <T> void removeAllSame(Collection<T> c1, Collection<T> c2) {
        c1.removeIf(c -> {
            if (c2.contains(c)) {
                c2.remove(c);
                return true;
            }
            return false;
        });
    }

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

    /**
     * 判断一个（bean）实体类对象是否为空<p>
     * 判断为空的标准为：<P>
     * <ol>
     * <li>如果实体类的属性为{@link String}那么字符串长度为0或为null就认为为空</li>
     * <li>如果属性为{@link Collection}的子类那么集合的长度为0或为null就认为为空</li>
     * <li>如果属性不为上述的就为null才认为为空</li>
     * </ol>
     *
     * @param obj 一个实体类（bean）对象
     * @return true：如果该实体类的所有属性都为空，false：其中的任意一个属性不为空
     */
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

    /**
     * 用户判断方法不灵活  已被弃用
     *
     * @param t
     * @param collection
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T extends StreamTree> T max(T t, Collection<T> collection) {
        T tt = null;
        int temp = -1;
        for (T t1 : collection) {
            int d = t.getTier(t1.getId());
            if (d > temp) {
                temp = d;
                tt = t1;
            }
        }
        return tt;
    }

    public static <N> N max(Collection<N> collection, BiFunction<N, Integer, Integer> fun) {
        N nn = null;
        int temp = -1;
        for (N n : collection) {
            int t = fun.apply(n, temp);
            if (t > temp) {
                temp = t;
                nn = n;
            }
        }
        return nn;
    }

    /**
     * 用用户的自己的判断方法查找集合中的最小值
     *
     * @param collection 要查找的集合
     * @param fun        用户自己的判断方法
     * @param <N>        集合的类类型
     * @return 最下值
     */
    public static <N> N min(Collection<N> collection, BiFunction<N, Integer, Integer> fun) {
        N nn = null;
        int temp = Integer.MAX_VALUE;
        for (N n : collection) {
            int t = fun.apply(n, temp);
            if (t < temp) {
                temp = t;
                nn = n;
            }
        }
        return nn;
    }

    /**
     * 添加跨域支持
     *
     * @param httpServletResponse 响应
     * @param httpServletRequest  请求
     */
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
