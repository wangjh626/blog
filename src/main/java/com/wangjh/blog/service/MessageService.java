package com.wangjh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangjh.blog.entity.Message;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public void messageCount(HttpServletRequest request, List<Message> messages) {
        // 消息未读数量
        int count = (int) messages.stream().filter(message -> message.getStatus() == 0).count();
        request.getSession().setAttribute("messageCount", count);
        messages.forEach(System.out::println);
    }

    /**
     * 用户未读消息的数量
     * @param user
     * @return
     */
    public int messageCount(User user) {
        // 消息未读数量
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver", user.getId()).eq("status", 0);
        return messageMapper.selectList(queryWrapper).size();
    }
}
