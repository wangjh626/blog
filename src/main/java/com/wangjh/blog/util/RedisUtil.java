package com.wangjh.blog.util;

import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

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
     * 根据 key 从缓存中获取一个 list
     * @param key
     * @return
     */
    public <T> List<T> getListObject(String key) {
        // 根据 key 从缓存中获取 list 的大小
        Long size = redisTemplate.opsForList().size(key);
        if (size != null) {
            // 如果 list 的大小不是 0，则返回该缓存
            return (List<T>) redisTemplate.opsForList().range(key, 0, size);
        } else {
            // 否则返回 null
            return null;
        }
    }

    /**
     * 根据 orderBy 对文章缓存进行排序，并获取缓存
     * @param key
     * @param orderBy
     * @param start
     * @param end
     * @return
     */
    public List<Article> getArticleListOrder(String key, String orderBy, int start, int end) {
        String comments = "comments";
        String publishDate = "publishDate";
        List<Object> list = getListObject(key);
        List<Article> articles = new ArrayList<>();
        for (Object o : list) {
            articles.add((Article) o);
        }
        if (StringUtils.equals(orderBy, comments)) {
            articles.sort((o1, o2) -> o2.getComments().compareTo(o1.getComments()));
        } else if (StringUtils.equals(orderBy, publishDate)) {
            articles.sort((o1, o2) -> o2.getPublishDate().compareTo(o1.getPublishDate()));
        }
        return articles.subList(start, end);
    }

    /**
     * 根据 orderBy 对消息缓存进行排序，并获取缓存
     * @param key
     * @param orderBy
     * @param start
     * @param end
     * @return
     */
    public List<Message> getMessageListOrder(String key, String orderBy, int start, int end) {
        // 根军 key 从缓存中获取消息的 list
        List<Message> messages = getListObject(key);
        // 将消息按照时间倒序排列
        messages.sort((o1, o2) -> o2.getGmtCreate().compareTo(o1.getGmtCreate()));
        return messages.subList(start, end);
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
