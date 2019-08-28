package com.wangjh.blog.interceptor;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.MessageService;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * 实现用户登录状态持久化
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 先获取所有 Cookie，看是否存在包含 token 的Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals("token", cookie.getName())) {
                    User user = userService.findByToken(cookie.getValue());
                    // 登录状态的过期时间为两天
                    long expireDate = 86400 * 1000L;
                    if (System.currentTimeMillis() - user.getRecentlyLanded() > expireDate) {
                        userService.logout(response, request);
                    } else {
                        request.getSession().setAttribute("user", user);
                    }
                    // 消息未读数
                    int messageCount = messageService.messageCount(user.getId());
                    request.getSession().setAttribute("messageCount", messageCount);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
