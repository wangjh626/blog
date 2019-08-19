package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import com.wangjh.blog.shiro.ShiroEncryption;
import com.wangjh.blog.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

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
     * @param user
     * @param result
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/login")
    public String login(@Validated User user, BindingResult result, Model model,
                        HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(user.getPhone())) {
            model.addAttribute("errorMessage", "请输入手机号");
            return "login";
        }
        if (result.getFieldError("phone") != null) {
            model.addAttribute("errorMessage", result.getFieldError("phone").getDefaultMessage());
            return "login";
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            model.addAttribute("errorMessage", "请输入密码");
            model.addAttribute("phone", user.getPhone());
            return "login";
        }
        if (result.getFieldError("password") != null) {
            model.addAttribute("errorMessage", "请输入正确的密码（6-16 位）");
            model.addAttribute("phone", user.getPhone());
            return "login";
        }
        // 1. 获取 Subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装用户数据
        // 根据手机号获取用户信息
        User dbUser = userService.findByPhone(user.getPhone());
        // 得到用户的 salt
        String salt = dbUser.getSalt();
        // 将密码转换为加密后的密码
        String encodedPassword = shiroEncryption.shiroEncryption(user.getPassword(), salt);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(dbUser.getPhone(), encodedPassword);
        // 3. 执行登录方法
        try {
            subject.login(usernamePasswordToken);
            // 登录成功
            // 给 user 设置一个 token
            userService.updateToken(dbUser, jwtUtil.createToken(dbUser));
            // 并将此 token 添加到 Cookie 中
            response.addCookie(new Cookie("token", dbUser.getToken()));
            request.getSession().setAttribute("user", dbUser);
            return "redirect:/";
        } catch (UnknownAccountException e) {
            model.addAttribute("errorMessage", "账号不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("errorMessage", "密码错误");
            // 如果密码输入错误则提示，并且回显已经输入的手机号
            model.addAttribute("phone", user.getPhone());
            return "login";
        }
    }
}
