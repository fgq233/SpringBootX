package com.security.demo.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

/**
 * Jwt 工具类
 */
public class JwtUtils {

    private static final String JWT_USER_KEY = "user";
    private static final String SECRET = "123456";
    private static final int EXPIRE = 30 * 60 * 1000;

    /**
     * 测试
     */
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "fgq");
        map.put("password", "666");
        String token = generateToken(map);
        System.out.println(token);

        getInfoFromToken(token);
    }


    /**
     * 生成 token
     */
    public static String generateToken(Object userInfo) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return Jwts.builder()
                .claim(JWT_USER_KEY, JSON.toJSONString(userInfo))
                .setId(createJTI())                     // 唯一的ID
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  // 到期时间
                .signWith(signatureAlgorithm, SECRET)   // 加密算法, 秘钥
                .compact();
    }

    /**
     * 解析token
     */
    private static Jws<Claims> parserToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 获取token中的用户信息
     */
    public static Object getInfoFromToken(String token) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        String id = body.getId();
        Date expiration = body.getExpiration();
        Object userInfo = body.get(JWT_USER_KEY);
        System.out.println(id);
        System.out.println(expiration);
        System.out.println(userInfo);

        return userInfo;
    }




}