package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String index() {
        return "blog";
    }
}
