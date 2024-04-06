package com.mybatisplus.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("sys_book")
public class Book  {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer price;

    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private User user;

}
