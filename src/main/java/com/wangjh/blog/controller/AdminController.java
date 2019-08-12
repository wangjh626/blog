package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private TagService tagService;

    /**
     * 后台管理页面
     *
     * @return
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin-index";
    }

    /**
     * 后台个人资料页面
     *
     * @return
     */
    @GetMapping("/admin/user")
    public String adminUser(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "admin-user";
    }
}
