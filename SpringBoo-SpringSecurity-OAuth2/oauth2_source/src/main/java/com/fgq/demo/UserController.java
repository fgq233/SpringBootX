package com.fgq.demo;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 使用令牌 access_token 访问
     * http://localhost:9002/user/getCurrentUser?access_token=713ea91e-80d7-4d48-95d0-0be601bf0ae5
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }

}
