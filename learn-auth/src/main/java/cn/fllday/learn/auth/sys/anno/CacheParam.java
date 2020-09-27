package cn.fllday.learn.auth.sys.anno;

import java.lang.annotation.*;

/**
 * @Author: gssznb
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheParam {

    String name() default "";

}
