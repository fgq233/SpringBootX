package com.shiro.demo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String password;

    private String salt;

    private List<String> resourceIds;

}
