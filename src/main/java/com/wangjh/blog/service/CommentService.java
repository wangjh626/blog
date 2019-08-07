package com.wangjh.blog.service;

import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.CommentExample;
import com.wangjh.blog.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 一篇博客下的所有评论
     * @param id    博客文章 id
     * @return
     */
    public List<Comment> listComments(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andPIdIsNull().andArticleIdEqualTo(id);
        commentExample.setOrderByClause("comment_date desc");
        return commentMapper.selectByExample(commentExample);
    }

    public List<Comment> listReplies(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andPIdIsNotNull().andArticleIdEqualTo(id);
        return commentMapper.selectByExample(commentExample);
    }
}
