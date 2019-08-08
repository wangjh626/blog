package com.wangjh.blog.controller;

import com.wangjh.blog.dto.CategoryDTO;
import com.wangjh.blog.dto.CommentDTO;
import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.CommentService;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ArticleController {

    private List<CategoryDTO> categories;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    /**
     * 获取一篇博客下的所有评论，以及评论底下的回复
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/article/{id}")
    public String artilce(@PathVariable("id") Long id, Model model) {
        // 根据博客 id 获取某一篇博客
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        // 获取某篇博客下的所有评论
        List<CommentDTO> comments = commentService.listComments(id);
        model.addAttribute("comments", comments);
        // 获取评论下的所有回复
        List<CommentDTO> replies = commentService.listReplies(id);
        model.addAttribute("replies", replies);
        return "article";
    }

    /**
     * 根据文章 id 删除文章
     * @param articleId
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteById(articleId);
        // 删除文章的同时也要删除该文章下的所有评论
        commentService.deleteAllComments(articleId);
        return "redirect:/articleTable";
    }

    /**
     * 查询博客的所有分类
     *
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
     *
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public ModelAndView listByCategory(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("article-table-category");
        modelAndView.addObject("categoryId", id);
        CategoryDTO category = categories.get(id);
        List<Article> articles = articleService.listByCategory(category.getName());
        modelAndView.addObject("articles", articles);
        modelAndView.setViewName("forward:/category/list/"+id);
        return modelAndView;
    }

    /**
     * 展示某个分类下的所有博客文章
     *
     * @return
     */
    @GetMapping("/category/list/{id}")
    public ModelAndView list(@PathVariable("id") Integer id,
                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = "15") Integer size) {
        ModelAndView modelAndView = new ModelAndView("article-table-category-list");
        // 根据 id 获取一个分类下的所有博客文章
        PaginationDTO<Article> paginationDTO = articleService.paginationByCategory(categories.get(id).getName(), page, size);
        modelAndView.addObject("paginationDTO", paginationDTO);
        return modelAndView;
    }
}
