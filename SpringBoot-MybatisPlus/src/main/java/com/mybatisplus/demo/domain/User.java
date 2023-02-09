package com.mybatisplus.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@TableName("tb_user")
public class User {

    @TableId(type = IdType.INPUT)
    private Long id;

    // TableField可标识字段和数据库名称不一致的
    @TableField("username")
    private String username;

    private String address;

    // 逻辑删除，默认查询会带上 yxzt = 1
    @TableLogic(value = "1", delval = "0")
    private Integer yxzt;

    // 数据库没有的字段
    @TableField(exist = false)
    private Long age;


    @TableField(exist = false)
    private List<Order> orderList;

}
