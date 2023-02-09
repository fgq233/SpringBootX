package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatisplus.demo.domain.Order;
import com.mybatisplus.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<Order> findByIf(Order order);

    List<Order> findByList(List<Integer> list);

    List<Order> findByList2(List<Integer> list);

    List<Order> findByUserId(Integer userId);

    List<Order> findOneToOne();

    List<User> findOneToMore(Integer id);

    @Select("SELECT * FROM tb_order")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "num", property = "num"),
//  column = "user_id" 是关联的键
            @Result(column = "user_id", property = "user", javaType = User.class, one = @One(select = "com.fgq.demo.mapper.UserMapper.selectById", fetchType = FetchType.EAGER)),
    })
    List<Order> findOneToOneX();

    @Select("SELECT * FROM tb_user where id = #{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "address", property = "address"),
            @Result(column = "yxzt", property = "yxzt"),
//  column = "id" 是关联的键
            @Result(column = "id", property = "orderList", javaType = List.class, many = @Many(select = "com.fgq.demo.mapper.OrderMapper.findByUserId", fetchType = FetchType.EAGER)),
    })
    List<User> findOneToMoreX(Integer userId);
}
