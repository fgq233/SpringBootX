package com.shiro.demo.service;

import com.shiro.demo.domain.User;
import com.shiro.demo.utils.PassWordUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    /**
     * 模拟根据用户名查询用户
     */
    public User findUserByName(String name) {
        User user = new User();
        user.setId(1L);
        user.setName(name);
        user.setPassword("666");
        return user;
    }

    /**
     * 模拟根据用户名查询用户(密码是加密的)
     */
    public User findUserByName2(String name) {
        User user = new User();
        user.setId(1L);
        user.setName(name);
        String sqlt = "fgq666666";
        user.setSalt(sqlt);
        // 数据库中密码是加密的
        user.setPassword(PassWordUtils.sha1("666", sqlt));
        return user;
    }


    /**
     * 模拟根据用户名查询角色
     */
    public List<String> findRoleByName(String name) {
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("dev");
        return list;
    }

    /**
     * 模拟根据用户名查询权限
     */
    public List<String> findPermissionByName(String name) {
        List<String> list = new ArrayList<>();
        list.add("user:add");
        list.add("user:del");
        list.add("user:list");
        return list;
    }

    /**
     * 模拟根据 userId 查询资源
     */
    public List<String> findResourcesIds(Long userId) {
        return Arrays.asList("1", "2", "3");
    }

}
