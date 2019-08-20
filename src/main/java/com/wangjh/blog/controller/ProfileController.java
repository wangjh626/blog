package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    /**
     * 修改个人资料
     * @return
     */
    @PostMapping("/admin/user")
    public String modify(@Validated User user, BindingResult result, HttpServletRequest request) {
        User dbUser = (User) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user.getUsername())) {
            user.setUsername(dbUser.getUsername());
        }
        userService.modifyProfile(dbUser.getId(), user.getUsername(), user.getEmail(), user.getPersonalBrief(),
                user.getAvatarImgUrl());
        request.getSession().setAttribute("user", user);
        return "redirect:/admin/user";
    }
}
