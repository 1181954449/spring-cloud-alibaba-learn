package cn.fllday.learn.auth.web;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ExceptionHandler(value = Exception.class)
    public AjaxResult exceptionHandler(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        log.error("系统错误: [ {} ]", e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

}
