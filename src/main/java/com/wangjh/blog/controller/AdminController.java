package com.wangjh.blog.controller;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
