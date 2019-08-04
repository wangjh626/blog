package com.wangjh.blog.controller;

import com.github.pagehelper.Page;
import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.UserMapper;
import com.wangjh.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page,
                      @RequestParam(name = "size", defaultValue = "5") Integer size) {
        PaginationDTO paginationDTO = articleService.list(page, size);
        List articles = paginationDTO.getData();
        model.addAttribute("paginationDTO", paginationDTO);
        model.addAttribute("articles", articles);
        return "blog";
    }
}
