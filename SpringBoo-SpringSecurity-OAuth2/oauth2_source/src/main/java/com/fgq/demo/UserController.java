package com.fgq.demo;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

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


    /**
     * 使用令牌 access_token 访问
     * http://localhost:9002/user/getCurrentJwtUser?access_token=713ea91e-80d7-4d48-95d0-0be601bf0ae5
     */
    @RequestMapping("/getCurrentJwtUser")
    public Object getCurrentJwtUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.replaceAll("bearer ", "");
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

}
