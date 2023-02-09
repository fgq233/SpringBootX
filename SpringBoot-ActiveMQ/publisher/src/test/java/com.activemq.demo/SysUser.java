package com.activemq.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    private String name;
    private int age;

}
