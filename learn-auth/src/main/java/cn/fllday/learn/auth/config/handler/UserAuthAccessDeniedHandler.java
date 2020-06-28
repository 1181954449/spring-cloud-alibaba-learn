package cn.fllday.learn.auth.config.handler;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: gssznb
 * 用户暂无权限处理
 */
@Component
@Slf4j
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        log.info("暂无权限:[ {} ],", e.getLocalizedMessage());
        AjaxResult authError = AjaxResult.error(ServiceExceptionEnum.AUTH_ERROR, e.getMessage());
        PrintWriter writer = response.getWriter();
        writer.write(authError.toString());
        writer.flush();
        writer.close();
    }
}
