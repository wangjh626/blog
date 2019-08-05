package com.wangjh.blog.controller;

import com.wangjh.blog.dto.CategoryDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ArticleController {

    private List<CategoryDTO> categories;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public String artilce(@PathVariable("id") Integer id, Model model) {
        // 根据博客 id 获取某一篇博客
        Article article = articleService.showArticle(id);
        model.addAttribute("article", article);
        // 获取某篇博客下的所有评论
        List<Comment> comments = commentService.list(id);
        model.addAttribute("comments", comments);
        return "article";
    }

    /**
     * 用户评论某一篇博客
     * @param articleId
     * @param content
     * @param request
     * @return
     */
    @PostMapping("/article/{id}")
    public String comment(@PathVariable("id") Integer articleId, @RequestParam("comment_content") String content,
                          HttpServletRequest request) {
        commentService.addComment(articleId, content, request);
        return "redirect:/article/" + articleId;
    }

    /**
     * 根据文章 id 删除文章
     * @param articleId
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Integer articleId) {
        articleService.deleteById(articleId);
        return "redirect:/articleTable";
    }

    /**
     * 查询博客的所有分类
     * @param model
     * @return
     */
    @GetMapping("/category")
    public String category(Model model) {
        categories = articleService.listCategories();
        model.addAttribute("categories", categories);
        return "article-table-category";
    }

    /**
     * 根据 id 获取到某个分类下的所有博客文章，并将数据传递给展示页面
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public ModelAndView listByCategory(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("article-table-category-list");
        // 根据 id 获取一个分类下的所有博客文章
        CategoryDTO category = categories.get(id);
        List<Article> articles = articleService.listByCategory(category.getName());
        modelAndView.addObject("articles", articles);
        // 通过 forward 定位到展示页面
        modelAndView.setViewName("forward:/category/list");
        return modelAndView;
    }

    /**
     * 展示某个分类下的所有博客文章
     * @return
     */
    @GetMapping("/category/list")
    public String list() {
        return "article-table-category-list";
    }
}
