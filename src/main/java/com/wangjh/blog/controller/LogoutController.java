package com.wangjh.blog.controller;

import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        userService.logout(response, request);
        return "redirect:/";
    }
}