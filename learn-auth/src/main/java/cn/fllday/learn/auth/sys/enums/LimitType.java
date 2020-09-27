package cn.fllday.learn.auth.sys.enums;

import cn.fllday.learn.auth.sys.anno.Limit;
import cn.fllday.learn.auth.utils.ip.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: gssznb
 */
public enum LimitType implements LimitGeneratorKey{
    /**
     *
     */
    IP {
        @Override
        public String key(Limit limit, HttpServletRequest request) {
            String realIp = IpUtils.getRealIp(request);
            StringBuilder sb = new StringBuilder();
            sb.append(realIp);
            sb.append("_");
            sb.append(request.getRequestURI());
            sb.append("_");
            sb.append(request.getMethod());
            sb.append(limit.name()).append("_").append(limit.key());
            return sb.toString();
        }
    },
}
