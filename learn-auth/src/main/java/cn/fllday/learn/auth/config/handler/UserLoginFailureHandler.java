package cn.fllday.learn.auth.config.handler;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: gssznb
 * 登录失败 处理逻辑
 */
@Component
@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        AjaxResult error = null;
        if (e instanceof UsernameNotFoundException){
            log.info("【登录失败】"+e.getLocalizedMessage());
            error = AjaxResult.error(ServiceExceptionEnum.AUTH_USERNAME_OR_PASSWORD_ERRPR, e.getMessage());
        } else if (e instanceof LockedException){
            log.info("【登录失败】"+e.getLocalizedMessage());
            error = AjaxResult.error(ServiceExceptionEnum.AUTH_LOCKED_ERROR, e.getMessage());
        } else if (e instanceof BadCredentialsException){
            log.info("【登录失败】"+e.getLocalizedMessage());
            error =  AjaxResult.error(ServiceExceptionEnum.AUTH_USERNAME_OR_PASSWORD_ERRPR, e.getMessage());
        } else {
            log.info("【登录失败】"+e.getLocalizedMessage());
            error = AjaxResult.error();
        }
        writer.write(error.toString());
        writer.flush();
        writer.close();
    }
}
