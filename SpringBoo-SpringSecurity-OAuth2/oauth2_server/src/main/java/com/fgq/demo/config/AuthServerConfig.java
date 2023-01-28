package com.fgq.demo.config;

import com.fgq.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()                              // 配置客户端信息存储在内存中
                .withClient("admin")            // 客户端 client_id
                .secret(passwordEncoder.encode("admin123456"))  // 客户端 client_secret
                .accessTokenValiditySeconds(3600)       // 访问token的有效期
                .refreshTokenValiditySeconds(864000)    // 刷新token的有效期
                .redirectUris("http://www.baidu.com")   // 重定向地址 redirect_uri，用于授权成功后跳转
                .autoApprove(false)                     // false 跳转到授权页面，true直接重定向并返回授权码
                .scopes("all")                          // 申请的权限范围
                .authorizedGrantTypes("authorization_code", "password"); // 授权模式
    }


    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }


}