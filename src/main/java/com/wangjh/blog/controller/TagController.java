package com.wangjh.blog.controller;

import com.wangjh.blog.dto.CommentDTO;
import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.dto.ResultDTO;
import com.wangjh.blog.dto.TagDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.CommentMapper;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TagController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    /**
     * 导航栏 --- 标签
     *
     * @return
     */
    @GetMapping("/tags")
    public String tags(Model model, HttpServletRequest request, String tag, @RequestParam(name = "page",
            defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "15") Integer size) {
        if (tag == null) {
            User user = (User) request.getSession().getAttribute("user");
            model.addAttribute("user", user);
            Set<String> allTag = new HashSet<>();
            TagDTO tagDTO = new TagDTO();
            List<Article> articles = articleService.findAll();
            for (int i = 0; i < articles.size(); i++) {
                tagDTO.setTags(articles.get(i).getArticleTags().split("，"));
                String[] tags = tagDTO.getTags();
                for (int j = 0; j < tags.length; j++) {
                    if (!allTag.contains(tags[j])) {
                        allTag.add(tags[j]);
                    }
                }
            }
            model.addAttribute("tags", allTag);
        } else {
            PaginationDTO paginationDTO = articleService.findByTag(tag, page, size);
            model.addAttribute("paginationDTO", paginationDTO);
            TagDTO tagDTO = new TagDTO();
            model.addAttribute("tagDTO", tagDTO);
            model.addAttribute("tagName", tag);
        }
        return "tags";
    }
}