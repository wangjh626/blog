package com.wangjh.blog.controller;

import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TableController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articleTable")
    public String table(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(name = "size", defaultValue = "15") Integer size) {
        PaginationDTO paginationDTO = articleService.list(page, size);
        model.addAttribute("paginationDTO", paginationDTO);
        List articles = paginationDTO.getData();
        model.addAttribute("articles", articles);
        return "article-table";
    }
}