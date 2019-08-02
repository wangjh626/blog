package com.wangjh.blog.service;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void register(String phone, String username, String password) {
        if (!StringUtils.isEmpty(phone) && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = new User();
            user.setPhone(phone);
            user.setUsername(username);
            user.setPassword(password);
            userMapper.insert(user);
        }
    }
}
