//package com.wangjh.blog.interceptor;
//
//import com.wangjh.blog.entity.User;
//import com.wangjh.blog.entity.UserExample;
//import com.wangjh.blog.mapper.UserMapper;
//import com.wangjh.blog.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Service
//public class SessionInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * 实现用户登录状态持久化
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 先获取所有 Cookie，看是否存在包含 token 的Cookie
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    // 从 Cookie 中获取 token，并根据 token 查询数据库中是否有该用户
//                    String token = cookie.getValue();
//                    User user = userService.findByToken(token);
//                    // 如果存在
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                        break;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
