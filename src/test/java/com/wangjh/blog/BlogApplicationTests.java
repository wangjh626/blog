package com.wangjh.blog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wangjh.blog.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * 加密
     */
    @Test
    public void jwt() {
        User user = new User();
        user.setId(1);
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
}
