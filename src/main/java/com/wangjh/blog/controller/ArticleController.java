package com.wangjh.blog.controller;

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
import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
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
    @PostMapping("/{id}")
    public String comment(@PathVariable("id") Integer articleId, @RequestParam("comment_content") String content,
                          HttpServletRequest request) {
        commentService.addComment(articleId, content, request);
        return "redirect:/article/" + articleId;
    }
}
