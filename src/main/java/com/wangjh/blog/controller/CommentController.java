package com.wangjh.blog.controller;

import com.wangjh.blog.dto.CommentDTO;
import com.wangjh.blog.dto.ResultDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.CommentMapper;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.MessageService;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * 添加评论
     * @param commentDTO
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    public Object postComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        // 创建一个新的评论
        Comment comment = new Comment();
        // 被评论的文章 id
        Long articleId = commentDTO.getArticleId();
        // 文章评论数加 1
        articleService.addCommentCount(articleId);
        comment.setArticleId(articleId);
        // 文章作者
        Article article = articleService.findById(articleId);
        comment.setOriginalAuthor(article.getAuthor());
        // 评论者
        User commentor = (User) request.getSession().getAttribute("user");
        // 评论者 id
        comment.setAnswererId(commentor.getId());
        // 评论者用户名
        comment.setAnswererUsername(commentor.getUsername());
        // 被评论者 id
        User respondent = userService.findByName(article.getAuthor());
        comment.setRespondentId(respondent.getId());
        // 被评论者用户名
        comment.setRespondentUsername(respondent.getUsername());
        // 评论日期
        comment.setCommentDate(System.currentTimeMillis());
        // 评论点赞数
        comment.setLikes(0);
        // 评论内容
        comment.setCommentContent(commentDTO.getCommentContent());
        commentMapper.insert(comment);
        // 添加消息通知
        messageService.addMessage(comment, article, commentor, respondent);

        return ResultDTO.successOf();
    }

    /**
     * 添加回复
     * @param commentDTO
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/reply")
    public Object postReply(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        // 创建一个新的回复
        Comment comment = new Comment();
        // 父 id
        Long pId = commentDTO.getParentId();
        comment.setParentId(pId);
        // 父评论
        Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
        // 被评论的文章 id
        Long articleId = commentDTO.getArticleId();
        // 文章评论数加 1
        articleService.addCommentCount(articleId);
        comment.setArticleId(articleId);
        // 文章作者
        Article article = articleService.findById(articleId);
        comment.setOriginalAuthor(article.getAuthor());
        // 评论者
        User commentor = (User) request.getSession().getAttribute("user");
        // 评论者 id
        comment.setAnswererId(commentor.getId());
        // 评论者用户名
        comment.setAnswererUsername(commentor.getUsername());
        // 被评论者 id
        Long respondentId = parentComment.getAnswererId();
        User respondent = userService.findById(respondentId);
        comment.setRespondentId(respondentId);
        // 被评论者用户名
        comment.setRespondentUsername(userService.findById(respondentId).getUsername());
        // 评论日期
        comment.setCommentDate(System.currentTimeMillis());
        // 评论点赞数
        comment.setLikes(0);
        // 评论内容
        comment.setCommentContent(commentDTO.getCommentContent());
        commentMapper.insert(comment);
        // 添加消息通知
        messageService.addMessage(comment, article, commentor, respondent);

        return ResultDTO.successOf();
    }
}