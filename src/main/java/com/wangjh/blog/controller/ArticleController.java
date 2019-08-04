package com.wangjh.blog.controller;

import com.wangjh.blog.entity.Article;
import com.wangjh.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id}")
    public String artilce(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.showArticle(id);
        model.addAttribute("article", article);
        return "article";
    }
}
