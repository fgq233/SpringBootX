package com.fgq.demo.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限管理配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建cookie对象
     */
    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("ShiroCookie");
        return simpleCookie;
    }

    /**
     * 创建安全管理器
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 管理realm
        securityManager.setRealm(shiroDbRealm());
        // 管理会话
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    /**
     * 自定义realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }

    /**
     * 会话管理器
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 关闭会话更新
        sessionManager.setSessionValidationSchedulerEnabled(false);
        // 启用cookie
        sessionManager.setSessionIdCookieEnabled(true);
        // 指定cookie的生成策略，浏览器访问项目中每个接口都会携带一个cookie，叫ShiroCookie
        sessionManager.setSessionIdCookie(simpleCookie());
        // 指定全局会话超时时间
        sessionManager.setGlobalSessionTimeout(3600000);
        return sessionManager;
    }

    /**
     * 创建 Shiro生命周期处理器
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    // ------------------------ aop增强（使用注解鉴权方式）------------------------

    /**
     * AOP式方法级权限检查
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 配合DefaultAdvisorAutoProxyCreator事项注解权限校验
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(defaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }


    // ------------------------ Shiro 过滤器配置------------------------


    /**
     * 过滤器链  (顺序从上到下,优先级依次降低)
     */
    private Map<String, String> filterChainMap() {
        Map<String, String> map = new LinkedHashMap<>();
        // 静态资源不拦截
        map.put("/static/**", "anon");
        // 登录链接不拦截
        map.put("/login", "anon");
        map.put("/appLogin", "anon");
        // 其他链接都是需要登录的
        map.put("/**", "authc");
        return map;
    }

    /**
     * 自定义的过滤器
     */
    private Map<String, Filter> customfilters() {
        Map<String, Filter> map = new HashMap<>();
        map.put("roles-or", new RolesOrFilter());
        return map;
    }

    /**
     * shiro过滤器管理
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilter.setSecurityManager(defaultWebSecurityManager());

        // 默认的登陆访问url
        shiroFilter.setLoginUrl("/login");
        // 登陆成功后跳转的url
        shiroFilter.setSuccessUrl("/");
        // 没有权限跳转的url
        shiroFilter.setUnauthorizedUrl("/error");

        // 添加自定义的过滤器
        shiroFilter.setFilters(customfilters());
        // 过滤器链
        shiroFilter.setFilterChainDefinitionMap(filterChainMap());
        return shiroFilter;
    }
}
