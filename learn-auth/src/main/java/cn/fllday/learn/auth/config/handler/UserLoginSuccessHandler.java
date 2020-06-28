package cn.fllday.learn.auth.config.handler;

import cn.fllday.learn.auth.utils.JwtTokenUtil;
import cn.fllday.learn.common.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: gssznb
 * 用户登陆成功逻辑处理
 */
@Component
@Slf4j
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) throws IOException, ServletException {
        User loginUser = (User) auth.getPrincipal();
        String token = jwtTokenUtil.createJwtAccessToken(loginUser);
        AjaxResult<String> success = AjaxResult.success(token);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(success.toString());
        writer.flush();
        writer.close();
    }
}
