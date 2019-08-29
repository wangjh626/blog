package com.wangjh.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wangjh.blog.entity.User;

import java.util.Date;

public class JwtUtil {

    public String createToken(User user) {
        String token =
                JWT.create().withKeyId(String.valueOf(user.getId())).withSubject(user.getUsername()).withIssuedAt(new Date()).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
