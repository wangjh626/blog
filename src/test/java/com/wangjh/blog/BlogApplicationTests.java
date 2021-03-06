package com.wangjh.blog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.ArticleExample;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.ArticleMapper;
import com.wangjh.blog.mapper.UserMapper;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.util.RedisUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate<String, Object> objectRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis() {
        List<Object> range = objectRedisTemplate.opsForList().range("allArticles", 0, -1);
        range.forEach(System.out::println);
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setPhone("18671288856");
        user.setUsername("haha");
        user.setPassword("123456a");

        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testSelect() {
        System.out.println("----- selectAll method test -----");
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(4, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void code() {
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();
        System.out.println(md5);
    }

    /**
     * 加密
     */
    @Test
    public void jwt() {
        User user = new User();
        user.setId(1L);
        user.setUsername("zzz");
        user.setPassword("940301");
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withKeyId(String.valueOf(user.getId()))
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
                .sign(Algorithm.HMAC256(user.getPassword()));
        System.out.println(token);
    }

    /**
     * 解密
     */
    @Test
    public void decode() {
        String token = "eyJraWQiOiIxIiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJ6enoiLCJleHAiOjE1NjYyNzMwOTMsImlhdCI6MTU2NjI3MzAzM30.im3SPqDKFVrHeZxu6w4wkn_z3cnUi5plBY5wg7j4T7Q";
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("940301")).build();
        DecodedJWT jwt = verifier.verify(token);
        String subject = jwt.getSubject();
        System.out.println("用户名：" + subject);
    }

    @Test
    public void findByToken() {
        String token = "eyJraWQiOiI5IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiLotbDotbAiLCJpYXQiOjE1NjQ4MzkwMjN9.Rnqj93xXU8Pg-aj_tib67f4Fe3H5nBud1C-uMcIphkY";
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users.get(0).getPhone() + ":" + users.get(0).getPassword());
    }
}
