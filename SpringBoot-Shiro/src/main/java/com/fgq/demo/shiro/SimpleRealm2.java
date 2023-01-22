package com.fgq.demo.shiro;

import com.fgq.demo.domain.User;
import com.fgq.demo.service.UserService;
import com.fgq.demo.utils.PassWordUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

public class SimpleRealm2 extends AuthorizingRealm {

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PassWordUtils.SHA1);
        matcher.setHashIterations(PassWordUtils.ITERATIONS);
        super.setCredentialsMatcher(matcher);
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        UserService userService = new UserService();
        User user = userService.findUserByName2(token.getUsername());

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user.getName(),                         // 主身份
                user.getPassword(),                     // 密码(密文)
                ByteSource.Util.bytes(user.getSalt()),  // 加密的盐
                this.getName());                        // realm名称
        return info;
    }

    /**
     * 鉴权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 拿到用户认证凭证信息
        String loginName = (String) principalCollection.getPrimaryPrincipal();
        // 从数据库中查询对应的角色、权限
        UserService userService = new UserService();
        List<String> permissions = userService.findPermissionByName(loginName);
        List<String> roles = userService.findRoleByName(loginName);
        // 构建授权对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}
