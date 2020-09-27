package cn.fllday.learn.auth.web;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.exception.LimitRequestException;
import cn.fllday.learn.exception.RepeatRequestException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
@RestControllerAdvice
@Slf4j
public class SysControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AjaxResult exceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, String> errMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            errMap.put(field, defaultMessage);
        }
        log.info("参数上报异常： [ {} ]", e.getMessage());
        return AjaxResult.error(ServiceExceptionEnum.USER_PARAMS_ERRPR, errMap);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public AjaxResult exceptionHandler(ConstraintViolationException e, HttpServletResponse response) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String, Object> errMap = new HashMap<>();
        for (ConstraintViolation c : constraintViolations) {
            errMap.put(c.getPropertyPath().toString(), c.getMessage());
            log.error("异常方法位置 ： [ {} ]", c.getRootBeanClass()+"#"+c.getPropertyPath());
        }
        log.info("参数上报异常: [ {} ]", e.getMessage());
        return AjaxResult.error(ServiceExceptionEnum.USER_PARAMS_ERRPR, errMap);
    }

    @ExceptionHandler(value = RepeatRequestException.class)
    public AjaxResult exceptionHandler(RepeatRequestException e) {
        log.error(e.getLocalizedMessage());
        return AjaxResult.error(ServiceExceptionEnum.SYS_REPEAT_REQUEST_ERROR);
    }

    @ExceptionHandler(value = LimitRequestException.class)
    public AjaxResult exceptionHandler(LimitRequestException e) {
        log.error(e.getLocalizedMessage());
        return AjaxResult.error(ServiceExceptionEnum.SYS_LIMIT_REQUEST_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public AjaxResult exceptionHandler(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        log.error("系统错误: [ {} ]", e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

}
