package cn.fllday.learn.auth.sys.enums;

import cn.fllday.learn.auth.sys.anno.Limit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: gssznb
 */
public interface LimitGeneratorKey {

    /**
     * 根据 ip 生成key
     * @param request
     * @return
     */
    String key(Limit limit, HttpServletRequest request);

}
