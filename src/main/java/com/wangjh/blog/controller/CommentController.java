package com.wangjh.blog.controller;

import com.wangjh.blog.dto.CommentDTO;
import com.wangjh.blog.dto.ResultDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.CommentMapper;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.CommentService;
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
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * 添加评论
     *
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
     *
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

    /**
     * 文章点赞
     *
     * @param commentDTO
     * @return
     */
    @PostMapping("/like")
    @ResponseBody
    public Object like(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        // 如果未登录则无法点赞
        if (user == null) {
            return ResultDTO.errorOf(201);
        }
        // 查询被点赞文章
        Article article = articleService.findById(commentDTO.getArticleId());
        // 文章作者不能给自己的文章点赞
        if (article.getAuthor().equals(user.getUsername())) {
            return ResultDTO.errorOf(202);
        }
        // 同一用户只能点赞一次
        Comment dbLike = commentService.findLike(article.getId(), user.getId());
        if (dbLike != null) {
            return ResultDTO.errorOf(203);
        }
        // 文章 likes 数量加一
        article.setLikes(article.getLikes() + 1);
        // 更新文章
        articleService.update(article);
        // 通知文章作者
        Comment like = new Comment();
        like.setArticleId(article.getId());
        like.setOriginalAuthor(article.getAuthor());
        like.setAnswererId(user.getId());
        like.setAnswererUsername(user.getUsername());
        like.setCommentDate(System.currentTimeMillis());
        like.setLikes(0);
        like.setCommentContent("点赞");
        commentMapper.insert(like);
        messageService.addMessage(like, article, user, article.getAuthor());
        return ResultDTO.successOf();
    }
}