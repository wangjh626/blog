package com.wangjh.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangjh.blog.entity.Message;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageMapper messageMapper;

    @GetMapping("/message")
    public String message(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver", user.getId());
        List<Message> messages = messageMapper.selectList(queryWrapper);
        model.addAttribute("messages", messages);
        return "message";
    }

    @PutMapping("/message/{messageId}")
    public void changeStatus(@PathVariable(name = "messageId") Long messageId) {
        Message message = messageMapper.selectByPrimaryKey(messageId);
        message.setStatus(1);
        int i = messageMapper.updateByPrimaryKey(message);
        System.out.println("更改状态" + i);
    }
}