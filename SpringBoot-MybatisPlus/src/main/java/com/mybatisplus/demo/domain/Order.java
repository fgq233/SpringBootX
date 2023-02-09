package com.mybatisplus.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("tb_order")
public class Order extends Model<Order> {

    @TableId(type = IdType.INPUT)
    private Long id;
    private String name;
    private Integer price;
    private Integer num;

    @TableField(exist = false)
    private User user;
}
