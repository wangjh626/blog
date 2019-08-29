package com.wangjh.blog.interceptor;

import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.MessageService;
import com.wangjh.blog.service.TagService;
import com.wangjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

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
                        // 用户的文章总数和标签总数
                        // 文章总数
                        int articlesCount;
                        // 标签总数
                        int tagsCount;
                        // 查询用户的所有文章
                        List<Article> allArticlesByUser = articleService.findAllByUser(user.getUsername());
                        if (allArticlesByUser != null) {
                            articlesCount = allArticlesByUser.size();
                        } else {
                            articlesCount = 0;
                        }
                        request.getSession().setAttribute("articlesCount", articlesCount);
                        // 查询用户的所有标签
                        Set<String> allTag = tagService.allTag(user);
                        if (allTag != null) {
                            tagsCount = allTag.size();
                        } else {
                            tagsCount = 0;
                        }
                        request.getSession().setAttribute("tagsCount", tagsCount);

                        // 消息未读数
                        int messageCount = messageService.messageCount(user.getId());
                        request.getSession().setAttribute("messageCount", messageCount);
                    }
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
