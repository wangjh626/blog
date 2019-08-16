package com.wangjh.blog.controller;

import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.dto.TagDTO;
import com.wangjh.blog.entity.Article;
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
        // 所有文章
        PaginationDTO paginationDTO = articleService.list(page, size, "");
        model.addAttribute("paginationDTO", paginationDTO);
        TagDTO tagDTO = new TagDTO();
        model.addAttribute("tagDTO", tagDTO);

        // 热门文章，根据文章评论数排序
        List<Article> hotArticles = articleService.hotArticles();
        model.addAttribute("hotArticles", hotArticles);
        return "blog";
    }
}
