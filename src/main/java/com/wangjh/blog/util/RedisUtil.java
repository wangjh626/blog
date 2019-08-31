package com.wangjh.blog.util;

import com.wangjh.blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 在缓存中存入一个对象
     *
     * @param key
     * @param object
     */
    public void setObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    /**
     * 在缓存中存入一个对象并设置过期时间
     *
     * @param key      键
     * @param object   对象
     * @param time     时间
     * @param timeUnit 时间单位
     */
    public void setObject(String key, Object object, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, object, time, timeUnit);
    }

    /**
     * 根据 key 在缓存中查询一个对象
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 在缓存中存入一个 list 对象并设置过期时间
     *
     * @param key
     * @param list
     * @param time
     * @param timeUnit
     */
    public void setListObject(String key, List list, Long time, TimeUnit timeUnit) {
        // 从右边添加，取出来的时候是按照时间降序排序
        redisTemplate.opsForList().rightPushAll(key, list);
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 获取缓存中的所有文章，并按照 orderBy 进行排序
     * @param orderBy
     * @return
     */
    public List<Article> getAllArticlesOrderBy(String orderBy) {
        List listObjectOrderBy = getListObjectOrderBy("allArticles", orderBy, 0L, redisTemplate.opsForList().size("allArticles"));
        List<Article> articleList = new ArrayList<>();
        for (Object o : listObjectOrderBy) {
            articleList.add((Article) o);
        }
        return articleList;
    }

    /**
     * 在缓存中取出一个 list 对象，并按照 orderBy 倒序排列
     * @param key
     * @param orderBy
     * @param start
     * @param end
     * @return
     */
    public List getListObjectOrderBy(String key, String orderBy, Long start, Long end) {
        SortQuery<String> query =
                SortQueryBuilder.sort(key).by(orderBy).order(SortParameters.Order.ASC).limit(start, end).build();
        return redisTemplate.sort(query);
    }

    /**
     * 修改过期时间
     *
     * @param key      键
     * @param time     时间
     * @param timeUnit 时间单位
     */
    public void expire(String key, Long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 根据 key 在缓存中删除一个对象
     *
     * @param key
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 从 list 缓存中获取一个对象
     * @param id
     * @return
     */
    public Article getOneObjectFromList(String key, Long id) {
        Long size = redisTemplate.opsForList().size(key);
        if (size != null) {
            System.out.println(size);
            List<Object> list = redisTemplate.opsForList().range(key, 0, size);
            if (list != null) {
                for (Object value : list) {
                    Article o = (Article) value;
                    if (o.getId().equals(id)) {
                        return o;
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }
}
