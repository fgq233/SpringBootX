package com.security.demo.service;

import com.security.demo.domain.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    /**
     * 模拟根据用户名查询用户
     */
    public SysUser findUserByName(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setId(1L);
        sysUser.setUsername(username);
        // SpringSecurity 默认要求密码要加密，此处模拟加密
        sysUser.setPassword(new BCryptPasswordEncoder().encode("666"));
        return sysUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = findUserByName(username);
        if (sysUser != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 模拟查询出的角色，前面要加上 ROLE_
            authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            authorities.add(new SimpleGrantedAuthority("ROLE_dev"));
            // 模拟查询出的权限
            authorities.add(new SimpleGrantedAuthority("userAdd"));
            authorities.add(new SimpleGrantedAuthority("userDel"));
            return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
        }
        return null;
    }
}
