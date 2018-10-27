package com.mebay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.mapper.RoleMapper;
import com.mebay.service.MenuService;
import com.mebay.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {
    private Logger logger = Logger.getLogger("nmka");
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuService menuService;
    @Test
    public void contextLoads() throws InterruptedException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match("/trip/**", "/trip?nmka=1"));
        System.out.println(antPathMatcher.match("/trip/**", "/trip/e?nmka=1"));
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        list.stream().filter((s) -> {
                logger.info(s + " >>" + System.currentTimeMillis());
                return s < 50;
        }).limit(49).forEach((s) -> logger.info(s + "   " + System.currentTimeMillis()));
        System.out.println(no10(100,10));
        System.out.println("ROLE_ENGINEER".matches("ROLE_[\\w]*_ADMIN"));
        Map<String, List<String>> nmka = new HashMap<>();
        nmka.put("12", new ArrayList<>(Arrays.asList("jk", "jdi")));
        nmka.put("22", new ArrayList<>(Arrays.asList("as1", "ka2")));
        nmka.put("32", new ArrayList<>(Arrays.asList("as3", "ka4")));
        try {
            System.out.println(new ObjectMapper().writeValueAsString(nmka));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<String> l = nmka.get("12");
        l.remove(1);
        l.add("nmka");
        try {
            System.out.println(new ObjectMapper().writeValueAsString(nmka));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        /*menuService.getMenusByRole(roleMapper.findRoleById(2L)).forEach(System.out::println);
        Map<Long, List<String>> menuIds = new HashMap<>();
        menuIds.put(1L, Arrays.asList("GET", "PUT"));
        menuIds.put(2L, Arrays.asList("GET", "PUT"));
        roleMapper.addMenuMethodToRole(1L, menuIds);
        Thread.sleep(10_000);
        roleMapper.removeMenuMethodByRole(1L, menuIds);*/
        Matcher matcher = Pattern.compile("/user([?/].*|$)").matcher("/user/1");
        System.out.println(matcher.matches());
        Matcher matcher2 = Pattern.compile("/[\\w/]*\\.json").matcher("/user/1.json");
        System.out.println(matcher2.matches());
        System.out.println(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{6,16}$").matcher("123D5a").matches());
        System.out.println("./dfj.jsjk".replaceFirst("\\.", ""));
    }

    public double no10(int m, double n) {
        if(n == 0) {
            return m;
        }
        return no10(m,n - 1) + m / Math.pow(2, n - 1);
    }


}
