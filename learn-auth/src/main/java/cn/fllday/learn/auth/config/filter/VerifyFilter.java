package cn.fllday.learn.auth.config.filter;

import cn.fllday.learn.auth.utils.ip.IpUtils;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.component.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author gssznb
 * @Date 2020/7/18
 * @Descript:
 */
@Component
public class VerifyFilter extends GenericFilterBean {

    @Value("${security.login}")
    public String loginURL;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();
        String method = req.getMethod();
        String error = "";
        if ("post".equalsIgnoreCase(method) && loginURL.equals(servletPath)) {
            String code = req.getParameter("code");
            if (code == null) {
                error = AjaxResult.error(ServiceExceptionEnum.AUTH_CODE_ERROR).toString();
                return;
            }
            String realIp = IpUtils.getRealIp(req);
            String redisCode = (String) redisUtils.get(realIp);
            redisUtils.del(realIp);
            if (code.equalsIgnoreCase(redisCode)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                error = AjaxResult.error(ServiceExceptionEnum.AUTH_CODE_ERROR).toString();
            }
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(error);
            out.flush();
            out.close();
        }else  {
            filterChain.doFilter(request, response);
        }
    }
}
