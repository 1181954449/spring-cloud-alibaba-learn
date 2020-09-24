package cn.fllday.learn.valid.anno;

import cn.fllday.learn.valid.IsStatus.IsStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsStatus.class}
)
public @interface Status {

    String[] status() ;

    String defaultVal();

    boolean required() default false;

    String message() default "状态值不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
