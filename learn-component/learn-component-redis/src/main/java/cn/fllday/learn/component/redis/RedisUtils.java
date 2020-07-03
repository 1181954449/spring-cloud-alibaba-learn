package cn.fllday.learn.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gssznb
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public RedisUtils() {
    }

    /**
     * 指定缓存失效时间
     * @param key    键
     * @param time   有效时间
     * @return
     */
    public boolean expire(String key,long time){
        if (time>0){
            redisTemplate.expire(key,time, TimeUnit.SECONDS);
            return true;
        }else {
            return false;
        }
    }

    /**
     *  根据key 获取过期时间
     * @param key  不能够为Null
     * @return
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }


    /**
     *  判断Key是否存在
     * @param key  键值
     * @return  true 存在，false 不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     *  删除缓存
     * @param key  可以传一个值，或者多个
     */
    public void del(String... key ) {
        if (key !=null && key.length > 0){
            if (key.length == 1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ==============================String===========================================


    /**
     *  普通缓存获取
     * @param key 键
     * @return z值
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     *  普通缓存放入
     * @param key   键
     * @param value value值
     * @return  true成功false失败
     */
    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     *  普通缓存放入并且设置时间
     * @param key     键
     * @param value  值
     * @param time   时间，time要大于0，如果小于将设置无期限
     * @return true 成功，false失败
     */
    public boolean set(String key,Object value,long time){
        try {
            if (time > 0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     *  递增
     * @param key 键
     * @param delta 要增加几
     * @return
     */
    public long incr(String key,long delta){
        if (delta < 0){
            throw new RuntimeException("递增因子必须要大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 递减
     * @param key    键
     * @param delta  要减少几
     * @return
     */
    public long decr(String key,long delta){
        if (delta < 0){
            throw new RuntimeException("递减因子必须要大于0");
        }
        return redisTemplate.opsForValue().increment(key,-delta);
    }

    // ====================================Map=====================================

    /**
     *  HashGet
     * @param key  键 不能为空
     * @param item 项 不能为空
     * @return 值
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * HashSet
     * @param key  键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     *  HashSet 并设置时间
     * @param key    键
     * @param map    对应多个键值
     * @param time   时间 （秒）
     * @return  true 成功 false失败
     */
    public boolean hmset(String key,Map<String,Object> map,long time){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            if (time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     *  向一张hash表中存放数据，如果不存在将会创建
     * @param key      键
     * @param item     项
     * @param value    值
     * @return   true 成功，false失败
     */
    public boolean hset(String key,String item,Object value){
        try {
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中存放数据，如果不存在将创建
     * @param key        键
     * @param item       项
     * @param value      值
     * @param time       时间（秒） 如果已经存在的hash表中有时间，那么这里将会替换原来的时间。
     * @return  true 成功 false 失败
     */
    public boolean hset(String key,String item,Object value,long time){
        try {
            redisTemplate.opsForHash().put(key,item,value);
            if (time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  删除Hash表中的值
     * @param key    键不能为空
     * @param item   项 可以使多个，不能为null
     */
    public void hdel(String key,Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     *  判断hash表中是否又该项的值
     * @param key    键 不能为null
     * @param item   项  不能为null
     * @return    true 存在,false 不存在
     */
    public boolean hHaskey(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }

    /**
     *  hash递增
     * @param key   键
     * @param item  项
     * @param by  要增加几
     * @return
     */
    public double hincr(String key,String item,double by){
        return redisTemplate.opsForHash().increment(key,item,by);
    }

    /**
     *  hash递增
     * @param key   键
     * @param item  项
     * @param by  要减少几
     * @return
     */
    public double hdecr(String key,String item,double by){
        return redisTemplate.opsForHash().increment(key,item,-by);
    }

    // =======================================Set====================================

    /**
     * 根据key获取set中的值
     * @param key   键
     * @return
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return null;
        }
    }

    /**
     *  根据valu从一个set中查询，是否存在
     * @param key     键
     * @param value   值
     * @return
     */
    public boolean sHasKey(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key,value);
        } catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key      键
     * @param values  值 可以是多个
     * @return  放入成功的个数
     */
    public long sSet(String key,Object... values){
        try {
            return redisTemplate.opsForSet().add(key,values);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return 0;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key      键
     * @param values  值 可以是多个
     * @param time  时间 （秒）
     * @return  放入成功的个数
     */
    public long sSetAndTime(String key,long time,Object... values){
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0){
                expire(key,time);
            }
            return count;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param key   键
     * @param values  值  可以是多个
     * @return
     */
    public long setRemove(String key,Object... values){
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return 0;
        }
    }

    // ============================list==============================

    /**
     * 获取List缓存的内容
     * @param key     键
     * @param start 开始
     * @param end   结束 0 到- 1 代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key,start,end);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return null;
        }
    }

    /**
     * 获取List缓存的长度
     * @param key  键
     * @return
     */
    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return 0;
        }
    }

    /**
     * 通过索引 获取list的值
     * @param key    键
     * @param index 索引  Index>=0 时，  下标从0开始，以此类推   index <= 0 时， - 1表示尾巴倒数第一个。类推
     * @return
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key,index);
        }catch (Exception e){
            e.printStackTrace(System.out);
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key,Object value){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 将list放入缓存，并且设置过期时间
     * @param key      键
     * @param value    值
     * @param time     过期时间（秒）
     * @return
     */
    public boolean lSet(String key,Object value,long time){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            if (time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key      键
     * @param value    值
     * @return
     */
    public boolean lSet(String key,List<Object> value){
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 将List放入缓存并设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key,List<Object> value,long time){
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
            if (time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 根据索引修改list中的值
     * @param key     键
     * @param index  索引
     * @param value  值
     * @return
     */
    public boolean lUpdateIndex(String key,long index,Object value){
        try {
            redisTemplate.opsForList().set(key,index,value);
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }
    }


    /**
     * 移除N个值为value的
     * @param key    键
     * @param count  移除多少个
     * @param value 值
     * @return  移除的个数
     */
    public long lRemove(String key,long count,Object value){
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return 0;
        }
    }
}
