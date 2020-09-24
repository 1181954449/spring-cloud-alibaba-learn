package cn.fllday.learn.user.oauth2.handler;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: gssznb
 * 用户未登录逻辑处理
 */
@Component
@Slf4j
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        AjaxResult unLogin = AjaxResult.error(ServiceExceptionEnum.AUTH_UNLOGIN_ERROR, e.getMessage());
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(unLogin.toString());
        writer.flush();
        writer.close();
    }
}
