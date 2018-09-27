package com.mebay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {
    private Logger logger = Logger.getLogger("nmka");

    @Test
    public void contextLoads() {
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
    }

    public double no10(int m, double n) {
        if(n == 0) {
            return m;
        }
        return no10(m,n - 1) + m / Math.pow(2, n - 1);
    }


}
