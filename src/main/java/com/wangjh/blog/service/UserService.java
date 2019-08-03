package com.wangjh.blog.service;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.UserMapper;
import com.wangjh.blog.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void register(String phone, String username, String password) {
        User user = new User();
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        userMapper.insert(user);
    }

    public void updateToken(User user, String token) {
        user.setToken(token);
        userMapper.updateByPrimaryKey(user);
    }

    public User findByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
