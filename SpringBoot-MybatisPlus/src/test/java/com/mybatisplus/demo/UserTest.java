package com.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.domain.User;
import com.mybatisplus.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class UserTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 增
     */
    @Test
    void add() {
        User user = new User();
        user.setId(666L);
        user.setUsername("fgq");
        user.setAddress("US");
        userMapper.insert(user);
    }

    /**
     * 改
     */
    @Test
    void update() {
//         根据id更新
//        User user = new User();
//        user.setId(666L);
//        user.setUsername("fgq666");
//        userMapper.updateById(user);

//        根据条件更新
        User user = new User();
        user.setUsername("郑一亿");
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", "郑爽爽");
        userMapper.update(user, qw);
    }

    /**
     * 删(如果有逻辑值, 会变为逻辑删除-更新操作)
     */
    @Test
    void del() {
//         根据id删除
//        userMapper.deleteById(666L);

//        根据条件Map删除
//        Map<String, Object> delMap = new HashMap<>();
//        delMap.put("username", "郑一亿");
//        userMapper.deleteByMap(delMap);

//       根据条件QueryWrapper删除
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        qw.eq("username", "郑一亿");
//        userMapper.delete(qw);

//        根据id批量删除
        List<Long> delIds = new ArrayList<>();
        delIds.add(1L);
        delIds.add(2L);
        delIds.add(3L);
        userMapper.deleteBatchIds(delIds);
    }

    /**
     * 查
     */
    @Test
    void get() {
//        根据id查询
//        User user = userMapper.selectById(666L);
//        System.out.println(user);

//        根据id批量查询
//        List<User> list = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
//        System.out.println(list);

//      根据条件单个查询，如果有多个结果会报错
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        qw.eq("username", "郑一亿");
//        User user = userMapper.selectOne(qw);
//        System.out.println(user);

//        查总数
        QueryWrapper<User> qw = new QueryWrapper<>();
        Integer count = userMapper.selectCount(qw);
        System.out.println(count);
    }

    /**
     * 分页
     */
    @Test
    void page() {
        IPage<User> iPage = new Page<>(1, 3);
        userMapper.selectPage(iPage, null);

        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页数量：" + iPage.getSize());
        System.out.println("总计页数：" + iPage.getPages());
        System.out.println("总计条数：" + iPage.getTotal());
        System.out.println("数据：" + iPage.getRecords());
    }

    /**
     * 查(3种写法都支持链式)
     */
    @Test
    void query() {
//        普通写法
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        qw.lt("id", 3).gt("id", 3);

//        QueryWrapper的lambda写法
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        qw.lambda().lt(User::getId, 3);

//        LambdaQueryWrapper的lambda写法
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.lt(User::getId, 10).gt(User::getId, 3);

        List<User> list = userMapper.selectList(lqw);
        System.out.println(list);
    }


    /**
     * 条件查询null值处理，省去if-else判断逻辑
     */
    @Test
    void queryX() {
        User user = new User();
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.lt(user.getId() != null, User::getId, user.getId());
        List<User> list = userMapper.selectList(lqw);
        System.out.println(list);
    }

    /**
     * 只查部分字段
     */
    @Test
    void queryX2() {
//        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
//        lqw.select(User::getId, User::getUsername);
//        List<User> list = userMapper.selectList(lqw);
//        System.out.println(list);

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("id", "username");
        List<User> list = userMapper.selectList(qw);
        System.out.println(list);
    }



    /**
     * 分组查总数
     */
    @Test
    void queryX3() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("count(id) as ct, address");
        qw.groupBy("address");
        List<Map<String, Object>> maps = userMapper.selectMaps(qw);
        System.out.println(maps);
    }

    /**
     * 各种条件查询
     */
    @Test
    void queryX4() {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
//         相等 eq
//        lqw.eq(User::getUsername, "柳岩").eq(User::getAddress, "湖南省衡阳市");

//         范围查询 lt、le、gt、ge、in、between、notBetween
//        lqw.lt(User::getId, 3);
//        lqw.between(User::getId, 1, 3);

//        模糊查询
//        lqw.like(User::getAddress, "湖南");
//        lqw.likeLeft(User::getAddress, "湖南");
//        lqw.likeRight(User::getAddress, "湖南");

//        排序
        lqw.orderByDesc(User::getId);
        List<User> list = userMapper.selectList(lqw);
        System.out.println(list);
    }


    /**
     * 自定义xml查询
     */
    @Test
    void queryY() {
        User user = userMapper.findById(666L);
        System.out.println(user);
    }
}
