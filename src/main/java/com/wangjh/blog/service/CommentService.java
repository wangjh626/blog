package com.wangjh.blog.service;

import com.wangjh.blog.dto.CommentDTO;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.CommentExample;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.CommentMapper;
import com.wangjh.blog.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 一篇博客下的所有评论
     * @param id    博客文章 id
     * @return
     */
    public List<CommentDTO> listComments(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdIsNull().andArticleIdEqualTo(id);
        commentExample.setOrderByClause("comment_date desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments == null || comments.size() == 0) {
            return null;
        }
        Set<Long> commentorsId = comments.stream().map(comment -> comment.getAnswererId()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentorsId);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getAnswererId()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public List<CommentDTO> listReplies(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdIsNotNull().andArticleIdEqualTo(id);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments == null || comments.size() == 0) {
            return null;
        }
        Set<Long> commentorsId = comments.stream().map(comment -> comment.getAnswererId()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentorsId);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getAnswererId()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    /**
     * 删除一篇文章下的所有评论
     * @param articleId
     */
    public void deleteAllComments(Long articleId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andArticleIdEqualTo(articleId);
        commentMapper.deleteByExample(commentExample);
    }
}
