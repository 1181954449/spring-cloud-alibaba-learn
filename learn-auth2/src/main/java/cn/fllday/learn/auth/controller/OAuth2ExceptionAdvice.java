package cn.fllday.learn.auth.controller;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: gssznb
 */
@RestControllerAdvice
@Slf4j
public class OAuth2ExceptionAdvice {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public AjaxResult<OAuth2Exception> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
        log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        e.printStackTrace();
        return AjaxResult.error();
    }

    @ExceptionHandler({Exception.class})
    public AjaxResult<OAuth2Exception> handleException(Exception e) throws Exception {
        log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        e.printStackTrace();
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler({ClientRegistrationException.class})
    public AjaxResult<OAuth2Exception> handleClientRegistrationException(Exception e) throws Exception {
        log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        e.printStackTrace();
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler({OAuth2Exception.class})
    public AjaxResult<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
        log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        e.printStackTrace();
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(InvalidGrantException.class)
    public AjaxResult handleException(InvalidGrantException e) throws Exception {
        log.error("handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        e.printStackTrace();
        return AjaxResult.error(ServiceExceptionEnum.AUTH_USERNAME_OR_PASSWORD_ERRPR, e.getMessage());
    }

}
