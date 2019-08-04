package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.TileObserver;
import java.security.interfaces.ECKey;

@Controller
public class PublishController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/publish")
    public String publishPage(HttpServletRequest request) {
        return "publish";
    }

    /**
     * 发布博客
     *
     * @param title
     * @param tags
     * @param content
     * @param category
     * @param request
     * @return
     */
    @PostMapping("/publish")
    public ModelAndView publish(@RequestParam("title") String title, @RequestParam("tags") String tags,
                                @RequestParam("category") String category, @RequestParam("content") String content,
                                HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("publish");
        // 当标题、标签、分类、博客内容为空时报错
        if (StringUtils.isEmpty(title)) {
            modelAndView.addObject("errorMessage", "标题不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(tags)) {
            modelAndView.addObject("errorMessage", "标签不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(content)) {
            modelAndView.addObject("errorMessage", "博客内容不能为空");
            return modelAndView;
        }
        // 从 session 中获取 user，即博客作者
        User user = (User) request.getSession().getAttribute("user");
        // 添加博客
        articleService.insert(title, tags, content, category, user);
        // 跳转回首页
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/publish/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("title", article.getArticleTitle());
        model.addAttribute("tags", article.getArticleTags());
        model.addAttribute("category", article.getArticleCategories());
        model.addAttribute("content", article.getArticleContent());
        return "publish";
    }

    @PostMapping("/publish/{id}")
    public String updateArticle(@PathVariable("id") Integer id, @RequestParam("title") String title,
                                @RequestParam("tags") String tags, @RequestParam("category") String category,
                                @RequestParam("content") String content) {
        articleService.update(id, title, tags, content, category);
        return "redirect:/";
    }
}
