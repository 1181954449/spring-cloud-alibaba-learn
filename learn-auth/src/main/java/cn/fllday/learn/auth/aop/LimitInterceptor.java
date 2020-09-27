package cn.fllday.learn.auth.aop;

import cn.fllday.learn.auth.sys.anno.Limit;
import cn.fllday.learn.exception.LimitRequestException;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: gssznb
 */
@Aspect
@Configuration
@Slf4j
public class LimitInterceptor {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    @Around("execution(public * *(..)) && @annotation(cn.fllday.learn.auth.sys.anno.Limit)")
    public Object interceptor(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        String key = limit.type().key(limit, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        ImmutableList<String> of = ImmutableList.of(StringUtils.join(limit.prefix(), key));
        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Object execute = redisTemplate.execute(redisScript, of, limit.count(), limit.period());
        if (!Objects.isNull(execute) && ((Number)execute).intValue() <= limit.count()) {
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException("服务异常");
            }
        } else {
            throw new LimitRequestException("请求过于频繁，请稍后再试");
        }
    }

    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }

}
