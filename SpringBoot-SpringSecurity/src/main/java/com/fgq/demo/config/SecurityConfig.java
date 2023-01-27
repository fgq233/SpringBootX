package com.fgq.demo.config;

import com.fgq.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        // 启动时创建 persistent_logins 表，若表已经创建会报错
        // repository.setCreateTableOnStartup(true);
        return repository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedPage("/noAuth.html");
        http.rememberMe()
                .tokenRepository(persistentTokenRepository())   // token 仓库
                .tokenValiditySeconds(60);                      // 有效时长
        http.logout()
                .logoutUrl("/logout")              // 注销登录接口
                .logoutSuccessUrl("/login.html");  // 注销成功跳转地址
        http.formLogin()
                .loginPage("/login.html")           // 登录页面的地址
                .loginProcessingUrl("/myLogin")     // 登录表单提交的 Controller 接口地址，SpringSecurity会自动处理
                .defaultSuccessUrl("/index")        // 登录成功后---跳转的地址
                .failureUrl("/login.html")          // 登录失败后---跳转的地址
                .usernameParameter("username")      // 表单中的用户名key
                .passwordParameter("password")      // 表单中的密码key
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/login.html", "/hello").permitAll()                       // 不拦截的地址，即无需认证也可以访问的地址
                .antMatchers("/index").hasRole("admin")                                 // 角色校验
                .antMatchers("/user").hasAnyAuthority("userAdd", "userDel")  // 权限校验
                .anyRequest().authenticated();      // authenticated()拦截，这里表示其他任何请求都要认证之后才能访问
        http.csrf().disable();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
