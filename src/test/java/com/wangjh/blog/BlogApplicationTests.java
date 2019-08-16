package com.wangjh.blog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.UserMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
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
                .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 1000))
                .sign(Algorithm.HMAC256(user.getPassword()));
        System.out.println(token);
    }

    /**
     * 解密
     */
    @Test
    public void decode() {
        String token = "eyJraWQiOiIxIiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJ6enoiLCJleHAiOjE1NjQ3MjQzMTAsImlhdCI6MTU2NDcyNDI5MH0.mXg4NoQDscfEGcIZ9SYTGGijLAEBqGjpPAbj89ajCa0";
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
