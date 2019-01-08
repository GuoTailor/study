package com.mebay.aliDevice;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by gyh on 2019/1/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AliController {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
