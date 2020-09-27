package cn.fllday.learn.auth.aop;

import cn.fllday.learn.auth.sys.anno.LocalLock;
import cn.fllday.learn.auth.utils.ip.IpUtils;
import cn.fllday.learn.component.redis.RedisUtils;
import cn.fllday.learn.exception.RepeatRequestException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gssznb
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Around("execution(public * *(..)) && @annotation(cn.fllday.learn.auth.sys.anno.LocalLock)")
    public Object interceptor(ProceedingJoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method clazzMethod = signature.getMethod();
        LocalLock localLock = clazzMethod.getAnnotation(LocalLock.class);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取真实ip地址/
        String realIp = IpUtils.getRealIp(request);
        // 获取请求路径
        String uri = request.getRequestURI();
        // 获取请求方式
        String method = request.getMethod();

        Map<String, String[]> parameterMap = request.getParameterMap();
        String kvJson = JSONObject.toJSONString(parameterMap);

        String key = getKey(realIp, uri, method, kvJson);
        if (!StringUtils.isEmpty(key)) {
            final boolean result = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                    connection.set(key.getBytes(), new byte[0], Expiration.from(localLock.expire(),
                                                                                localLock.timeUnit()),
                                    RedisStringCommands.SetOption.SET_IF_ABSENT));
            if (!result) {
                throw new RepeatRequestException("请勿重复请求");
            }
        }
        try {
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("服务异常");
        }
    }

    public String getKey(String ip, String url, String method, String kvJson) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip);
        sb.append("_");
        sb.append(url);
        sb.append("_");
        sb.append(method);
        sb.append("_");
        sb.append(kvJson);
        return sb.toString();

    }
}
