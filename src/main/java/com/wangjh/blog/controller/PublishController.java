package com.wangjh.blog.controller;

import com.alibaba.druid.util.StringUtils;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import org.apache.ibatis.annotations.Mapper;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.TileObserver;
import java.security.interfaces.ECKey;

@Controller
public class PublishController {

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转到发布博客页面
     * @param request
     * @return
     */
    @GetMapping("/publish")
    public String publishPage(HttpServletRequest request) {
        return "publish";
    }

    /**
     * 发布博客
     * @param title
     * @param tags
     * @param content
     * @param category
     * @param request
     * @return
     */
    @PostMapping("/publish")
    public ModelAndView publish(@RequestParam("title") String title, @RequestParam("category") String category,
                                @RequestParam("tags") String tags, @RequestParam("type") String type,
                                @RequestParam("content") String content, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("publish");
        // 当标题、标签、分类、博客内容为空时报错
        if (StringUtils.isEmpty(title)) {
            modelAndView.addObject("errorMessage", "标题不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(category)) {
            modelAndView.addObject("errorMessage", "博客分类不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(tags)) {
            modelAndView.addObject("errorMessage", "标签不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(type)) {
            modelAndView.addObject("errorMessage", "类型不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(content)) {
            modelAndView.addObject("errorMessage", "博客内容不能为空");
            return modelAndView;
        }
        // 从 session 中获取 user，即博客作者
        User user = (User) request.getSession().getAttribute("user");
        // 添加博客
        if (type.equals("原创") || type.equals("转载")) {
            articleService.insert(title, category,tags, type, content, user);
        } else {
            modelAndView.addObject("errorMessage", "类型有误，请输入原创或转载");
            return modelAndView;
        }
        // 跳转回首页
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    /**
     * 跳转到修改博客页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("title", article.getArticleTitle());
        model.addAttribute("category", article.getArticleCategories());
        model.addAttribute("tags", article.getArticleTags());
        model.addAttribute("type", article.getArticleType());
        model.addAttribute("content", article.getArticleContent());
        return "publish";
    }

    /**
     * 修改博客
     * @param id
     * @param title
     * @param category
     * @param tags
     * @param type
     * @param content
     * @return
     */
    @PostMapping("/publish/{id}")
    public ModelAndView updateArticle(@PathVariable("id") Integer id, @RequestParam("title") String title,
                                      @RequestParam("category") String category, @RequestParam("tags") String tags,
                                      @RequestParam("type") String type, @RequestParam("content") String content) {
        ModelAndView modelAndView = new ModelAndView("publish");
        if (type.equals("原创") || type.equals("转载")) {
            articleService.update(id, title, category,tags, type, content);
        } else {
            modelAndView.addObject("errorMessage", "类型有误，请输入原创或转载");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/articleTable");
        return modelAndView;
    }
}
