package cn.fllday.learn.auth.sys.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gssznb
 * @Desc: 锁的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {

    /**
     * redis 锁前缀
     * @return
     */
    String prefix() default "";

    /**
     * 失效时间
     * @return
     */
    int expire() default 5;

    /**
     * 时间单位  默认为秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 分隔符 默认为 ：
     * @return
     */
    String delimiter() default ":";

}
