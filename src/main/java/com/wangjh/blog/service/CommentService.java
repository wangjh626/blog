package com.wangjh.blog.service;

import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.CommentExample;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    public void addComment(Integer articleId, String content, HttpServletRequest request) {
        Comment comment = new Comment();
        // 被评论文章 id
        Article article = articleService.findById(articleId);
        comment.setArticleId(Long.valueOf(articleId));
        // 被评论文章的原作者
        comment.setOriginalAuthor(article.getAuthor());
        // 评论者 id
        User commentor = (User) request.getSession().getAttribute("user");
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
        comment.setCommentContent(content);
        // 添加评论
        commentMapper.insert(comment);
    }

    /**
     * 一篇博客下的所有评论
     * @param id    博客文章 id
     * @return
     */
    public List<Comment> list(Integer id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andArticleIdEqualTo(Long.valueOf(id));
        return commentMapper.selectByExample(commentExample);
    }
}
