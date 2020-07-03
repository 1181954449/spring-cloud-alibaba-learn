package cn.fllday.learn.component.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: gssznb
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义缓存管理器，注入RedisCacheManager（不使用springboot默认提供的），
     * 下面缓存管理器配置了缓存失效时间，(如果有其他需求，需要重新定义缓存管理器，在使用缓存注解时指定对应的缓存管理器)
     * 过期时间只对Cache的那几个注解有效比如（@Cacheable，@CachePut），跟redisTemplate对象添加的缓存无关
     * 以及cache注解存取数据的序列化设置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        //（取出数据序列化配置）
        // 配置在使用 @Cacheable(value="'stus'",key="'stu:1'")注解，当第二次从缓存中读取
        // 数据时，无法将json字符串转换为java Bean的问题，不配置则抛出异常
        // （java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to
        // com.springboot.enty.Stu）
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //（存入数据序列化配置）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration redisCacheConfiguration = config
                //配置缓存失效时间100秒
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration)
                .build();
        return cacheManager;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        Jackson2JsonRedisSerializer jackson2 = new Jackson2JsonRedisSerializer(Object.class);
        jackson2.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2);
        redisTemplate.setKeySerializer(jackson2);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        Jackson2JsonRedisSerializer jackson2 = new Jackson2JsonRedisSerializer(String.class);
        jackson2.setObjectMapper(om);
        stringRedisTemplate.setValueSerializer(jackson2);
        stringRedisTemplate.setKeySerializer(jackson2);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

}
