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


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 封装根据 Comment 获取 CommentDTO 的方法
     * @param commentExample
     * @return
     */
    private List<CommentDTO> getCommentDTOS(CommentExample commentExample) {
        // 根据 CommentExample 获取所有评论
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        // 如果评论为空则返回 null
        if (comments == null || comments.size() == 0) {
            return null;
        }

        /**
         * 如果评论不为空，则根据评论获取评论者的 id
         * stream()：创建一个管道
         * map()：将评论与评论者 id 一一映射
         * distinct()：去重操作，将重复的评论者 id 去除
         * collect(Collectors.toList())：将最后的结果转换成 List 集合形式
         */
        List<Long> userIds = comments.stream().map(Comment::getAnswererId).distinct().collect(Collectors.toList());

        // 根据用户 id 获取所有用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        // 将用户 id 与用户一一映射，得到一个用户 Map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        // 将 Comment 转换成 CommentDTO 形式的 List 集合
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            // 将 Comment 的所有属性赋给 CommentDTO
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getAnswererId()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    /**
     * 一篇博客下的所有评论
     * @param articleId    博客文章 id
     * @return
     */
    public List<CommentDTO> listComments(Long articleId) {
        // 根据文章 id 按时间倒序获取所有的评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdIsNull().andArticleIdEqualTo(articleId);
        commentExample.setOrderByClause("comment_date desc");
        return getCommentDTOS(commentExample);
    }

    /**
     * 列出所有文章的评论
     * @return
     */
    public List<Comment> listComments() {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andArticleIdIsNotNull();
        return commentMapper.selectByExample(commentExample);
    }

    /**
     * 一篇博客下的所有回复
     * @param id
     * @return
     */
    public List<CommentDTO> listReplies(Long id) {
        // 根据文章 id 获取所有回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdIsNotNull().andArticleIdEqualTo(id);
        return getCommentDTOS(commentExample);
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
