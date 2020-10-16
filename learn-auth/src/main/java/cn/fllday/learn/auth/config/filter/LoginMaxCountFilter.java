package cn.fllday.learn.auth.config.filter;

import cn.fllday.learn.auth.utils.ip.IpUtils;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.component.redis.RedisUtils;
import cn.hutool.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: gssznb
 */
@Component
@Slf4j
public class LoginMaxCountFilter extends GenericFilter {

    @Value("${security.login}")
    public String loginURL;

    private Integer maxCount = 5;

    private Integer initCount = 1;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        String realIp = IpUtils.getRealIp(req);
        String servletPath = req.getServletPath();
        String method = req.getMethod();
        String error = "";
        if (Method.POST.name().equalsIgnoreCase(method) && loginURL.equals(servletPath)) {
            String username = req.getParameter("userName");
            String maxKey = realIp+"_"+username;
            Integer count = (Integer) redisUtils.get(maxKey);
            if (count == null) {
                redisUtils.set(maxKey, initCount, 60 * 20);
            } else if (count > this.maxCount) {
                ServiceExceptionEnum authMaxCountError = ServiceExceptionEnum.AUTH_MAX_COUNT_ERROR;
                authMaxCountError.setMsg(String.valueOf(redisUtils.getExpire(maxKey)));
                error = AjaxResult.error(authMaxCountError).toString();
                rsp.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(error);
                out.flush();
                out.close();
                return;
            } else {
                redisUtils.set(maxKey, count+1, 60 * 20);
            }
        }
        filterChain.doFilter(req, rsp);
    }
}
