package com.fgq.demo;


import com.fgq.demo.shiro.SimpleRealm2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroTest {


    @Test
    void loginTest() {
        // 1、创建 SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 2、使用工具类让安全管理器生效
        SecurityUtils.setSecurityManager(securityManager);
        // 3、设置 Realm
//        securityManager.setRealm(new SimpleRealm());
        securityManager.setRealm(new SimpleRealm2());
        // 4、使用工具类获得 Subject 主体
        Subject subject = SecurityUtils.getSubject();
        // 5、模拟请求的账户密码 token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("fgq", "666");
        // 6、使用Subject 主体去登录认证(认证失败会抛出异常)
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 7、打印认证结果
        System.out.println("登录结果:" + subject.isAuthenticated());
    }


    @Test
    void test() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);
        securityManager.setRealm(new SimpleRealm2());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("fgq", "666");
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (subject.isAuthenticated()) {
            // 角色检测
            System.out.println(subject.hasRole("admin"));
            try {
                subject.checkRole("superAdmin");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 权限检测
            System.out.println(subject.isPermitted("user:list"));
            try {
                subject.checkPermissions("user:add", "user:del");
                System.out.println("具有user增、删权限");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
