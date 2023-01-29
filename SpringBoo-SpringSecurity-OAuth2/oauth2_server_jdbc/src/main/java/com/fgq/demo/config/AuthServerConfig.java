package com.fgq.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;


    /**
     * 客户端信息数据来源---来自数据库(oauth_client_details表)
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 授权码保存策略---使用数据库存储(oauth_code表)
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * token令牌保存策略---使用数据库存储(oauth_access_token表)
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * token令牌管理
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(jdbcClientDetailsService());    // 客户端信息数据来源
        tokenServices.setSupportRefreshToken(true);             // 支持刷新 token
        tokenServices.setTokenStore(jdbcTokenStore());          // token保存策略
        tokenServices.setAccessTokenValiditySeconds(3600);      // token 有效期 1h
        tokenServices.setRefreshTokenValiditySeconds(864000);   // 刷新令牌有效期 24h
        return tokenServices;
    }



//    ★★★★★★★★★★★★  主要是重写3个config()方法，配置 oauth2 客户端信息、端点数据存储、安全规则   ★★★★★★★★★★★★

    /**
     * 配置客户端信息数据来源
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * oauth2 端点数据存储配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)  {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)            // 认证管理器(密码模式需要)
                .authorizationCodeServices(authorizationCodeServices())  // 授权码保存策略
                .tokenStore(jdbcTokenStore())                            // token令牌保存策略
                .tokenServices(tokenServices());                         // token令牌管理
    }

    /**
     * oauth2 端点安全访问配置，也就是 OAuth2 自带的那几个端点 URL 安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")          // 放行 /oauth/token_key，不用认证就可以访问
                .checkTokenAccess("isAuthenticated()")  // 开启 /oauth/check_token 安全认证，需要认证才可以访问
                .allowFormAuthenticationForClients();   // 允许表单认证
    }

}
