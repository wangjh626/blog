package com.wangjh.blog.controller;

import com.wangjh.blog.dto.CommentDTO;
import com.wangjh.blog.dto.PaginationDTO;
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
            List<CommentDTO> messages = commentService.listMessage();
            model.addAttribute("messages", messages);
        } else {
            PaginationDTO paginationDTO = articleService.findByTag(tag, page, size);
            model.addAttribute("paginationDTO", paginationDTO);
            TagDTO tagDTO = new TagDTO();
            model.addAttribute("tagDTO", tagDTO);
            model.addAttribute("tagName", tag);
        }
        return "tags";
    }

    /**
     * 导航栏 --- 标签页下的留言
     * @return
     */
    @PostMapping("/tags")
    public String leaveMessages(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        Comment comment = new Comment();
        // 获取回复用户
        User user = (User) request.getSession().getAttribute("user");
        // 设置回复用户 id
        comment.setAnswererId(user.getId());
        // 设置回复用户的 username
        comment.setAnswererUsername(user.getUsername());
        comment.setCommentDate(System.currentTimeMillis());
        comment.setLikes(0);
        // 设置回复内容
        comment.setCommentContent(commentDTO.getCommentContent());
        commentMapper.insert(comment);
        return "forward:/tags";
    }
}