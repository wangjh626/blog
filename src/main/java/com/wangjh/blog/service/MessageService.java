package com.wangjh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.Message;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.MessageMapper;
import com.wangjh.blog.mapper.UserMapper;
import com.wangjh.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户未读消息的数量
     *
     * @param userId
     * @return
     */
    public int messageCount(Long userId) {
        // 消息未读数量
        User user = userMapper.selectByPrimaryKey(userId);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver", user.getId()).eq("status", 0);
        return messageMapper.selectList(queryWrapper).size();
    }

    /**
     * 添加评论或者回复的消息
     *
     * @param comment
     * @param article
     * @param notifier
     * @param receiver
     */
    public void addMessage(Comment comment, Article article, User notifier, User receiver) {
        redisUtil.deleteObject("messages");
        // 消息通知（如果评论或者回复自己的博客则不需要通知）
        Message message = new Message();
        message.setNotifier(notifier.getId());
        message.setNotifierName(notifier.getUsername());
        message.setOuterId(article.getId());
        message.setOuterTitle(article.getArticleTitle());
        if (comment.getParentId() == null) {
            message.setReceiver(receiver.getId());
            message.setType(0);
        } else {
            message.setReceiver(comment.getRespondentId());
            message.setType(1);
        }
        message.setGmtCreate(comment.getCommentDate());
        message.setStatus(0);
        // 自己在自己的博客中添加评论或者自己回复自己的评论不需要进行消息通知
        if (!message.getNotifier().equals(message.getReceiver())) {
            messageMapper.insert(message);
        }
    }

    /**
     * 添加点赞消息
     *
     * @param comment
     * @param article
     * @param notifier
     * @param receiverName
     */
    public void addMessage(Comment comment, Article article, User notifier, String receiverName) {
        redisUtil.deleteObject("messages");
        Message message = new Message();
        message.setNotifier(notifier.getId());
        message.setNotifierName(notifier.getUsername());
        message.setOuterId(article.getId());
        message.setOuterTitle(article.getArticleTitle());
        User receiver = userService.findByName(receiverName);
        message.setReceiver(receiver.getId());
        message.setType(2);
        message.setGmtCreate(comment.getCommentDate());
        message.setStatus(0);
        messageMapper.insert(message);
    }
}
