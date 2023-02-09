package com.shiro.demo.shiro;

import com.shiro.demo.domain.ShiroUser;
import com.shiro.demo.domain.User;
import com.shiro.demo.service.UserService;
import com.shiro.demo.utils.PassWordUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PassWordUtils.SHA1);
        matcher.setHashIterations(PassWordUtils.ITERATIONS);
        super.setCredentialsMatcher(matcher);
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        UserService userService = new UserService();
        User user = userService.findUserByName2(token.getUsername());
        // 构建 ShiroUser，存储更详细的用户信息
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());
        shiroUser.setName(user.getName());
        shiroUser.setPassword(user.getPassword());
        shiroUser.setSalt(user.getSalt());
        shiroUser.setResourceIds(userService.findResourcesIds(user.getId()));
        return new SimpleAuthenticationInfo(shiroUser, shiroUser.getPassword(), ByteSource.Util.bytes(shiroUser.getSalt()), this.getName());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 拿到用户认证凭证信息
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        // 从数据库中查询对应的角色、权限
        UserService userService = new UserService();
        List<String> permissions = userService.findPermissionByName(shiroUser.getName());
        List<String> roles = userService.findRoleByName(shiroUser.getName());
        // 构建授权对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}
