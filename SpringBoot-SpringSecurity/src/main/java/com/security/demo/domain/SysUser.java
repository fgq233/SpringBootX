package com.security.demo.domain;

import lombok.Data;

@Data
public class SysUser {

    private Long id;

    private String username;

    private String password;

    private String salt;

}
