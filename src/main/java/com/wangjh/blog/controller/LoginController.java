package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import com.wangjh.blog.shiro.ShiroEncryption;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiroEncryption shiroEncryption;

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Shiro 的登录验证
     *
     * @param phone
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("phone") String phone, @RequestParam("password") String password, Model model,
                        HttpServletRequest request) {
        if (StringUtils.isEmpty(phone)) {
            model.addAttribute("errorMessage", "请输入账号");
            return "login";
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("errorMessage", "请输入密码");
            // 如果没有输入密码则提示，并且回显已经输入的手机号
            model.addAttribute("phone", phone);
            return "login";
        }
        // 1. 获取 Subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装用户数据
        // 根据手机号获取用户信息
        User user = userService.findByPhone(phone);
        // 得到用户的 salt
        String salt = user.getSalt();
        // 将密码转换为加密后的密码
        String encodedPassword = shiroEncryption.shiroEncryption(password, salt);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(phone, encodedPassword);
        // 3. 执行登录方法
        try {
            subject.login(usernamePasswordToken);
            // 登录成功
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } catch (UnknownAccountException e) {
            model.addAttribute("errorMessage", "账号不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("errorMessage", "密码错误");
            // 如果密码输入错误则提示，并且回显已经输入的手机号
            model.addAttribute("phone", phone);
            return "login";
        }
    }
}
