package com.wangjh.blog.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroEncryption {
    public String shiroEncryption(String password, String salt) {
        // 加密算法
        String algorithmName = "MD5";
        // 设置加密次数
        int times = 10;
        // 返回加密后的密码
        return new SimpleHash(algorithmName, password, salt, times).toString();
    }
}
