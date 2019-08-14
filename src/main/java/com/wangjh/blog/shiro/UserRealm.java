package com.wangjh.blog.shiro;

import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        return null;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        // 编写 Shiro 判断逻辑
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByPhone(token.getUsername());
        if (user == null) {
            // 账号不存在
            return null;
        }
        // 判断密码
        return new SimpleAuthenticationInfo("", user.getPassword(), "");
    }
}
