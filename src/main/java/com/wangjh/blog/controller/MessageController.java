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

    /**
     * 获取一个用户的所有消息通知
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/message")
    public String message(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver", user.getId()).orderByDesc("gmt_create");
        List<Message> messages = messageMapper.selectList(queryWrapper);
        model.addAttribute("messages", messages);
        return "message";
    }

    /**
     * 将消息设置为已读
     * @param messageId
     * @return
     */
    @PutMapping("/message/{messageId}")
    public String changeStatus(@PathVariable(name = "messageId") Long messageId) {
        Message message = messageMapper.selectByPrimaryKey(messageId);
        message.setStatus(1);
        messageMapper.updateByPrimaryKey(message);
        return "redirect:/article/" + message.getOuterId();
    }
}
