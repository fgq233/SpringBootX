package com.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatisplus.demo.domain.User;
import com.mybatisplus.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class UserQueryTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 各种条件查询
     */
    @Test
    void queryX() {
        // 相等 eq
        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(User::getAge, 18);
        userMapper.selectList(lqw1).forEach(System.out::println);

        // 不等于ne
        LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
        lqw2.ne(User::getId, 4);
        userMapper.selectList(lqw2).forEach(System.out::println);

        // 小于lt、大于gt
        LambdaQueryWrapper<User> lqw3 = new LambdaQueryWrapper<>();
        lqw3.gt(User::getId, 2).lt(User::getId, 5);
        userMapper.selectList(lqw3).forEach(System.out::println);

        // 小于等于le、大于等于ge
        LambdaQueryWrapper<User> lqw4 = new LambdaQueryWrapper<>();
        lqw4.ge(User::getId, 2).le(User::getId, 5);
        userMapper.selectList(lqw4).forEach(System.out::println);

        // in、notIn
        LambdaQueryWrapper<User> lqw5 = new LambdaQueryWrapper<>();
        lqw5.in(User::getId, 1, 3, 6);
//        lqw5.notIn(User::getId, 1, 3, 6);
        userMapper.selectList(lqw5).forEach(System.out::println);

        // between、notBetween
        LambdaQueryWrapper<User> lqw6 = new LambdaQueryWrapper<>();
        lqw6.between(User::getId, 1, 3);
//        lqw6.notBetween(User::getId, 1, 3);
        userMapper.selectList(lqw6).forEach(System.out::println);

        // 模糊查询 like、likeLeft、likeRight、notLike、notLikeLeft、notLikeRight
        LambdaQueryWrapper<User> lqw7 = new LambdaQueryWrapper<>();
        lqw7.like(User::getEmail, "test");
//        lqw7.likeLeft(User::getEmail, "test");
//        lqw7.likeRight(User::getEmail, "test");
//        lqw7.notLike(User::getEmail, "test");
        userMapper.selectList(lqw7).forEach(System.out::println);

        // isNull、isNotNull
        LambdaQueryWrapper<User> lqw8 = new LambdaQueryWrapper<>();
        lqw8.isNull(User::getEmail);
//        lqw8.isNotNull(User::getEmail);
        userMapper.selectList(lqw8).forEach(System.out::println);

        // and
        LambdaQueryWrapper<User> lqw9 = new LambdaQueryWrapper<>();
        lqw9.gt(User::getId, 3);
        lqw9.and(wrapper -> wrapper.like(User::getName, "fgq"));
        userMapper.selectList(lqw9).forEach(System.out::println);

        // or
        LambdaQueryWrapper<User> lqw10 = new LambdaQueryWrapper<>();
        lqw10.gt(User::getId, 5);
        lqw10.or().lt(User::getId, 2);
        userMapper.selectList(lqw10).forEach(System.out::println);

        // nested   效果：id > ? and (name = ? or email like ?)
        LambdaQueryWrapper<User> lqw11 = new LambdaQueryWrapper<>();
        lqw11.gt(User::getId, 2);
        lqw11.nested(i -> i.eq(User::getName, "fgq").or().like(User::getEmail, "test5"));
        userMapper.selectList(lqw11).forEach(System.out::println);

        // exists、notExists
        LambdaQueryWrapper<User> lqw12 = new LambdaQueryWrapper<>();
        lqw12.exists("select 1 from sys_book where user_id = 66");
        userMapper.selectList(lqw12).forEach(System.out::println);

        // 排序 orderBy、orderByAsc、orderByDesc
        LambdaQueryWrapper<User> lqw13 = new LambdaQueryWrapper<>();
        lqw13.orderBy(true, false, User::getId);
//        lqw13.orderByAsc(User::getId);
//        lqw13.orderByDesc(User::getId);
        userMapper.selectList(lqw13).forEach(System.out::println);

        // 分组 groupBy、having
        LambdaQueryWrapper<User> lqw14 = new LambdaQueryWrapper<>();
        lqw14.select(User::getAge);
        lqw14.groupBy(User::getAge);
        lqw14.having("age > {0}", 20);
        userMapper.selectMaps(lqw14).forEach(System.out::println);
    }


    /**
     * 查询(3种写法都支持链式)
     */
    @Test
    void query1() {
        // 普通写法
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.gt("id", 3).lt("id", 6);
        userMapper.selectList(qw).forEach(System.out::println);

        // QueryWrapper的lambda写法
        QueryWrapper<User> qw2 = new QueryWrapper<>();
        qw2.lambda().lt(User::getId, 3);
        userMapper.selectList(qw2).forEach(System.out::println);

        // LambdaQueryWrapper 写法
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.gt(User::getId, 3).lt(User::getId, 6);
        userMapper.selectList(lqw).forEach(System.out::println);
    }


    /**
     * null值处理，SQL中动态拼接查询条件，省去if-else判断逻辑
     * 若无 id 值，则 SELECT * FROM sys_user WHERE is_deleted=0
     * 若有 id 值，则 SELECT * FROM sys_user WHERE is_deleted=0 AND (id < ?)
     */
    @Test
    void query2() {
        User user = new User();
        user.setId(4L);
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.lt(user.getId() != null, User::getId, user.getId());
        userMapper.selectList(lqw).forEach(System.out::println);
    }

    /**
     * 只查部分字段,，其余字段为null
     */
    @Test
    void query3() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("id", "name");
        userMapper.selectList(qw).forEach(System.out::println);

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.select(User::getId, User::getEmail);
        userMapper.selectList(lqw).forEach(System.out::println);
    }


    /**
     * 分组查总数
     */
    @Test
    void query4() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("count(id) as ct, age");
        qw.groupBy("age");
        List<Map<String, Object>> list = userMapper.selectMaps(qw);
        list.forEach(System.out::println);
    }


}
