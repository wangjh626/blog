package com.wangjh.blog;

import com.wangjh.blog.shiro.ShiroEncryption;
import com.wangjh.blog.util.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.wangjh.blog.mapper")
@EnableCaching
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public ShiroEncryption shiroEncryption() {
        return new ShiroEncryption();
    }
}
