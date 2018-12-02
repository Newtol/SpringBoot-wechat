package cn.newtol.weixin.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 公众号：Newtol
 * @Description:    Redis工具类
 * @Date: Created in 18:55 2018/11/9
 */
@Service
public class RedisUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
    * @Author: REN
    * @Description: string有过期时间
    * @Date: Created in 20:01
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param timeUnit 计时方式
    */
    public void setString(String key, String value,long timeout,TimeUnit timeUnit){
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value,timeout,timeUnit);
    }

    /**
    * @Author: REN
    * @Description:  string无过期时间
    * @Date: Created in 20:00
    * @param: key
    */
    public void setString(String key,String value){
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * get redis: string类型
     * @param key key
     * @return
     */
    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * set redis: hash类型
     * @param key key
     * @param filedKey filedkey
     * @param value value
     */
    public void setHash(String key, String filedKey, String value){
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key,filedKey, value);
    }

    public void setHash(String key , Map<String,String> map){
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.putAll(key,map);
    }

    /**
     * get redis: hash类型
     * @param key key
     * @param filedkey filedkey
     * @return
     */
    public String getHash(String key, String filedkey){
        return (String) stringRedisTemplate.opsForHash().get(key, filedkey);
    }

}

