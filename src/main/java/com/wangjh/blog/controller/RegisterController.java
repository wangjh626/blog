package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import com.wangjh.blog.shiro.ShiroEncryption;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiroEncryption shiroEncryption;

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
    @PostMapping("/register")
    public ModelAndView registerUser(@RequestParam String phone, @RequestParam String username,
                               @RequestParam String password){
        ModelAndView modelAndView = new ModelAndView("register");
        if (phone.length() != 11) {
            modelAndView.addObject("errorMessage", "请输入 11 位手机号");
            return modelAndView;
        } else {
            // 检查该手机号是否已经注册
            User dbUser = userService.findByPhone(phone);
            if (dbUser != null) {
                modelAndView.addObject("errorMessage", "该手机号已注册");
                return modelAndView;
            }
        }
        if (StringUtils.isEmpty(username)) {
            modelAndView.addObject("phone", phone);
            modelAndView.addObject("errorMessage", "请输入用户名");
            return modelAndView;
        }
        if (StringUtils.isEmpty(password)) {
            modelAndView.addObject("phone", phone);
            modelAndView.addObject("username", username);
            modelAndView.addObject("errorMessage", "请输入密码");
            return modelAndView;
        }
        // 使用 Shiro 自带的工具类生成 salt
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encodedPassword = shiroEncryption.shiroEncryption(password, salt);
        userService.register(phone, username, salt, encodedPassword);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
