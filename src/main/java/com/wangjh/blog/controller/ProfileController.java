package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    /**
     * 修改个人资料
     * @return
     */
    @PostMapping("/admin-user")
    public String modify(@RequestParam("name") String username, @RequestParam("email") String email,
                         @RequestParam("intro") String intro, @RequestParam(value = "avatar") String avatar,
                         HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userService.modifyProfile(user.getId(), username, email, intro, avatar);
        return "redirect:/admin/user";
    }

}
