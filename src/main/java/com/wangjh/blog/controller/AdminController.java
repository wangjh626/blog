package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    /**
     * 后台管理页面
     * @return
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin-index";
    }

    /**
     *
     * @return
     */
    @GetMapping("/admin-user")
    public String adminUser(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "admin-user";
    }

}
