//package com.wangjh.blog.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisUtil {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 在缓存中存入一个对象
//     * @param key
//     * @param object
//     */
//    public void setObject(String key, Object object) {
//       redisTemplate.opsForValue().set(key, object);
//    }
//
//    /**
//     * 在缓存中存入一个对象并设置过期时间
//     * @param key   键
//     * @param object    对象
//     * @param time  时间
//     * @param timeUnit  时间单位
//     */
//    public void setObject(String key, Object object, Long time, TimeUnit timeUnit) {
//        redisTemplate.opsForValue().set(key, object, time, timeUnit);
//    }
//
//    /**
//     * 修改过期时间
//     * @param key   键
//     * @param time  时间
//     * @param timeUnit  时间单位
//     */
//    public void expire(String key, Long time, TimeUnit timeUnit) {
//        redisTemplate.expire(key, time, timeUnit);
//    }
//
//    /**
//     * 根据 key 在缓存中查询一个对象
//     * @param key
//     * @return
//     */
//    public Object getObject(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * 根据 key 在缓存中删除一个对象
//     * @param key
//     */
//    public void deleteObject(String key) {
//        redisTemplate.delete(key);
//    }
//}
