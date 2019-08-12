package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.dto.TagDTO;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Set;

@Controller
public class TagController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * 导航栏 --- 标签
     *
     * @return
     */
    @GetMapping("/tags")
    public String tags(Model model, HttpServletRequest request, String tag, @RequestParam(name = "page",
            defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "15") Integer size) {
        if (tag == null) {
            User user = (User) request.getSession().getAttribute("user");
            model.addAttribute("user", user);
            // 获取文章的所有标签
            Set<String> allTag = tagService.allTag(null);
            model.addAttribute("tags", allTag);
        } else {
            PaginationDTO paginationDTO = articleService.findByTag(tag, page, size);
            model.addAttribute("paginationDTO", paginationDTO);
            TagDTO tagDTO = new TagDTO();
            model.addAttribute("tagDTO", tagDTO);
            model.addAttribute("tagName", tag);
        }
        return "tags";
    }

    /**
     * 后台博客标签页面
     *
     * @return
     */
    @GetMapping("/admin/tags")
    public String adminTags(HttpServletRequest request, Model model) {
        // 从 Session 中获取用户
        User user = (User) request.getSession().getAttribute("user");
        // 根据用户查询该用户的所有文章，并获取文章的所有标签
        Set<String> allTag = tagService.allTag(user);
        model.addAttribute("tags", allTag);
        return "admin-tags";
    }

    /**
     * 后台展示一个标签下的所有文章
     *
     * @param tag
     * @param model
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/admin/tags/{tag}")
    public String adminTagArticle(@PathVariable("tag") String tag, Model model,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "15") Integer size) {
        PaginationDTO paginationDTO = articleService.findByTag(tag, page, size);
        model.addAttribute("paginationDTO", paginationDTO);
        return "admin-tags";
    }
}