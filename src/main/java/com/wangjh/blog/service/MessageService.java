package com.wangjh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangjh.blog.entity.Message;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.MessageMapper;
import com.wangjh.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户未读消息的数量
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
}
