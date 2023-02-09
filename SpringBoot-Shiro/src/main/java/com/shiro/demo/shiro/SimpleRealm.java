package com.shiro.demo.shiro;

import com.shiro.demo.domain.User;
import com.shiro.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class SimpleRealm extends AuthorizingRealm {


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        UserService userService = new UserService();
        User user = userService.findUserByName(token.getUsername());

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user.getName(),
                user.getPassword(),
                this.getName());
        return info;
    }

    /**
     * 鉴权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
