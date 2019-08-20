package com.wangjh.blog.service;

import com.wangjh.blog.entity.Comment;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.entity.UserExample;
import com.wangjh.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void register(String phone, String username, String salt, String password) {
        User user = new User();
        user.setPhone(phone);
        user.setUsername(username);
        user.setSalt(salt);
        user.setPassword(password);
        userMapper.insert(user);
    }

    public void updateToken(User user, String token) {
        user.setToken(token);
        userMapper.updateByPrimaryKey(user);
    }

    public User findByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByName(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        return userMapper.selectByExample(userExample).get(0);
    }

    /**
     * 根据评论 id 获取评论者的用户名
     * @param comments
     * @return
     */
    public List<User> findByComment(List<Comment> comments) {
        List<User> users = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getAnswererId());
            users.add(user);
        }
        return users;
    }

    /**
     * 修改个人资料
     * @param username
     * @param email
     * @param intro
     * @param avatar
     * @return
     */
    public void modifyProfile(Long id, String username, String email, String intro, String avatar) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPersonalBrief(intro);
        user.setAvatarImgUrl(avatar);
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据用户 id 获取用户
     * @param id
     */
    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    public User findByPhone(String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * 退出登录
     * @param response
     * @param request
     */
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 用户的登录状态
     * @param response
     * @param request
     */
    public void loginStatus(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals("token", cookie.getName())) {
                    User user = findByToken(cookie.getValue());
                    // 登录状态的过期时间为两天
                    long expireDate = 86400 * 1000L;
                    if (System.currentTimeMillis() - user.getRecentlyLanded() > expireDate) {
                        logout(response, request);
                    } else {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
    }
}
