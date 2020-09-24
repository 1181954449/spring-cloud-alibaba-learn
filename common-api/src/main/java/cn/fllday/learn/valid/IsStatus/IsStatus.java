package cn.fllday.learn.valid.IsStatus;

import cn.fllday.learn.valid.anno.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
@Slf4j
public class IsStatus implements ConstraintValidator<Status, String> {

    private String[] status;
    private boolean required;

    @Override
    public void initialize(Status constraintAnnotation) {
        this.status = constraintAnnotation.status();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String sex, ConstraintValidatorContext constraintValidatorContext) {
        log.info("stringList: [ {} ]", sex);
        if (required) {
            if (StringUtils.isEmpty(sex)) {
                return true;
            }  else {
                for (String s : status) {
                    if (s.equals(sex)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            for (String s : status) {
                if (s.equals(sex)) {
                    return true;
                }
            }
            return false;
        }

    }
}
