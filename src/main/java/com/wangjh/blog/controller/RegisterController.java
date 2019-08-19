package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import com.wangjh.blog.shiro.ShiroEncryption;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
     *
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 实现用户注册功能，注册完成后跳转到登录页面
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/register")
    public ModelAndView registerUser(@Validated User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (result.getFieldError("phone") != null) {
            modelAndView.addObject("errorMessage", result.getFieldError("phone").getDefaultMessage());
            return modelAndView;
        }
        if (result.getFieldError("username") != null) {
            modelAndView.addObject("errorMessage", result.getFieldError("username").getDefaultMessage());
            modelAndView.addObject("phone", user.getPhone());
            return modelAndView;
        }
        if (result.getFieldError("password") != null) {
            modelAndView.addObject("errorMessage", result.getFieldError("password").getDefaultMessage());
            modelAndView.addObject("phone", user.getPhone());
            modelAndView.addObject("username", user.getUsername());
            return modelAndView;
        }
        // 使用 Shiro 自带的工具类生成 salt
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encodedPassword = shiroEncryption.shiroEncryption(user.getPassword(), salt);
        userService.register(user.getPhone(), user.getUsername(), salt, encodedPassword);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
