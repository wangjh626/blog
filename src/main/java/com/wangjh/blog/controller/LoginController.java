package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.UserMapper;
import com.wangjh.blog.service.UserService;
import com.wangjh.blog.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

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
     * 实现用户登录功能
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("phone") String phone, @RequestParam("password") String password,
                              HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");

        if (StringUtils.isEmpty(phone)) {
            modelAndView.addObject("errorMessage", "请输入手机号");
            return modelAndView;
        } else {
            modelAndView.addObject("phone", phone);
        }

        if (StringUtils.isEmpty(password)) {
            modelAndView.addObject("errorMessage", "请输入密码");
            return modelAndView;
        } else {
            // 根据手机和密码从数据库或查询用户
            UserExample userExample = new UserExample();
            userExample.createCriteria().andPhoneEqualTo(phone).andPasswordEqualTo(password);
            List<User> users = userMapper.selectByExample(userExample);
            // 如果用户存在，则跳转到博客首页，否则提示错误
            if (users.size() != 0) {
                User user = users.get(0);
                request.getSession().setAttribute("user", user);
                String token = jwtUtil.createToken(user);
                userService.updateToken(user, token);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                modelAndView.setViewName("redirect:/");
                return modelAndView;
            } else {
                modelAndView.addObject("errorMessage", "手机或密码输入错误");
                return modelAndView;
            }
        }
    }
}
