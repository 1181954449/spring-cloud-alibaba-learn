package cn.fllday.learn.auth.sys.anno;

import cn.fllday.learn.auth.sys.enums.LimitType;

import java.lang.annotation.*;
import java.util.UUID;

/**
 * @Author: gssznb
 * 接口限流注解
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Limit {

    /**
     * 资源名称
     * @return
     */
    String name() default "";

    /**
     * 资源key
     * @return
     */
    String key() default "";

    /**
     * 资源 prefix
     * @return
     */
    String prefix() default "";

    /**
     * 给定的时间段
     * @return
     */
    int period() default 100;

    /**
     * 最多访问资源次数
     * @return
     */
    int count() default 10;

    /**
     * 接口访问类型
     * @return
     */
    LimitType type() default LimitType.IP;

}
