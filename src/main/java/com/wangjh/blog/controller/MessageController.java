package com.wangjh.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangjh.blog.entity.Message;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.MessageMapper;
import com.wangjh.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MessageController {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取一个用户的所有消息通知
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/message")
    public String message(Model model, HttpServletRequest request) {
        // 从 Session 中获取用户
        User user = (User) request.getSession().getAttribute("user");
        // 按时间倒序从 redis 中获取所有 Message
        List<Message> redisMessages = redisUtil.getMessageListOrder("messages", "gmtCreate", 0,
                Objects.requireNonNull(redisTemplate.opsForList().size("messages")).intValue());
        if (redisMessages.isEmpty()) {
            // 如果 redis 中没有消息的缓存，则从数据库中获取所有消息
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.isNotNull("id").orderByDesc("gmt_create");
            List<Message> messageList = messageMapper.selectList(queryWrapper);
            // 如果用户 id 和消息接收者的 id 相同，则存入 messages 中
            List<Message> messages = new ArrayList<>();
            for (Message message : messageList) {
                if (Objects.equals(message.getReceiver(), user.getId())) {
                    messages.add(message);
                }
            }
            // 将所有消息添加至 redis 的 messages 缓存中
            redisUtil.setListObject("messages", messageList, 2L, TimeUnit.HOURS);
            // 将用户消息传给前端页面
            model.addAttribute("messages", messages);
        } else {
            // 如果 redis 缓存中存在 messages 缓存，则取出该用户的所有消息
            List<Message> messages = new ArrayList<>();
            for (Message message : redisMessages) {
                if (Objects.equals(message.getReceiver(), user.getId())) {
                    messages.add(message);
                }
            }
            // 将该用户的缓存消息传给前端页面
            model.addAttribute("messages", messages);
        }
        return "message";
    }

    /**
     * 将消息设置为已读
     *
     * @param messageId
     * @return
     */
    @PutMapping("/message/{messageId}")
    public String changeStatus(@PathVariable(name = "messageId") Long messageId) {
        // 先删除消息缓存
        redisUtil.deleteObject("messages");
        // 根据消息 id 从数据库中获取消息
        Message message = messageMapper.selectByPrimaryKey(messageId);
        // 将消息的状态设为 1，表示已经读取
        message.setStatus(1);
        // 跟新该消息
        messageMapper.updateByPrimaryKey(message);
        return "redirect:/article/" + message.getOuterId();
    }
}
