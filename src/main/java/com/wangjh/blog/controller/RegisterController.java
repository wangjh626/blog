package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.exception.RegisterException;
import com.wangjh.blog.service.UserService;
import com.wangjh.blog.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 实现用户注册功能，注册完成后跳转到登录页面
     * @param phone
     * @param username
     * @param password
     * @return
     */
    @PostMapping("register")
    public ModelAndView registerUser(@RequestParam("phone") String phone, @RequestParam("username") String username,
                                     @RequestParam("password") String password, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("register");
        if (phone.length() != 11) {
            modelAndView.addObject("errorMessage", "请输入 11 位手机号");
            return modelAndView;
        } else {
            modelAndView.addObject("phone", phone);
        }
        if (StringUtils.isEmpty(username)) {
            modelAndView.addObject("errorMessage", "请输入用户名");
            return modelAndView;
        } else {
            modelAndView.addObject("username", username);
        }
        if (StringUtils.isEmpty(password)) {
            modelAndView.addObject("errorMessage", "请输入密码");
            return modelAndView;
        }
        userService.register(phone, username, password);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

}
