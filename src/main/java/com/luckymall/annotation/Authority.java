package com.luckymall.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Authority {
    /**
     * 过期检查
     */
    String [] role() default "";

    /**
     * 过期检查
     */
    boolean permission() default true;
}
