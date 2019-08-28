package com.wangjh.blog.controller;

import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.dto.TagDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size, HttpServletRequest request) {
        // 用户文章总数和标签总数
        // 文章总数
        int articlesCount;
        // 标签总数
        int tagsCount;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            // 查询用户的所有文章
            List<Article> allArticlesByUser = articleService.findAllByUser(user.getUsername());
            if (allArticlesByUser != null) {
                articlesCount = allArticlesByUser.size();
            } else {
                articlesCount = 0;
            }
            request.getSession().setAttribute("articlesCount", articlesCount);
            // 查询用户的所有标签
            Set<String> allTag = tagService.allTag(user);
            if (allTag != null) {
                tagsCount = allTag.size();
            } else {
                tagsCount = 0;
            }
            request.getSession().setAttribute("tagsCount", tagsCount);
        }

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
