package com.fgq.demo;

import com.fgq.demo.domain.Order;
import com.fgq.demo.domain.User;
import com.fgq.demo.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OrderTest {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 动态sql: if标签
     */
    @Test
    void query() {
        Order order = new Order();
//        order.setId(101L);
        order.setNum(1);
        List<Order> list = orderMapper.findByIf(order);
        System.out.println(list);
    }


    /**
     * 动态sql: foreach标签
     */
    @Test
    void query2() {
        List<Integer> params = Arrays.asList(101, 102, 103);
//        List<Order> list = orderMapper.findByList(params);
        List<Order> list = orderMapper.findByList2(params);
        System.out.println(list);
    }


    /**
     * 一对一查询
     */
    @Test
    void query3() {
//        List<Order> list = orderMapper.findOneToOne();
        List<Order> list = orderMapper.findOneToOneX();
        for (Order order : list) {
            System.out.println(order);
        }
    }


    /**
     * 一对多查询
     */
    @Test
    void query4() {
//        List<User> list = orderMapper.findOneToMore(3);
        List<User> list = orderMapper.findOneToMoreX(3);
        for (User user : list) {
            System.out.println(user);
        }
    }

}
