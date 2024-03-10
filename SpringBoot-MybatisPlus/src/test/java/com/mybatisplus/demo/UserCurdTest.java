package com.mybatisplus.demo;

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
class UserCurdTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 插入
     */
    @Test
    void insert() {
        User user = new User();
        user.setName("fgq");
        user.setAge(18);
        user.setEmail("fgq@163.com");
        userMapper.insert(user);
    }

    /**
     * 删除，PS：如果实体有 @TableLogic，则会变成逻辑删除(更新)
     *
     */
    @Test
    void delete() {
        // 根据 id 删除
        userMapper.deleteById(6);

        // 根据 entity 条件删除
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", "Billie");
        userMapper.delete(qw);

        // 根据 columnMap 条件删除
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("name", "Sandy");
        userMapper.deleteByMap(delMap);

        // 根据 id 批量删除
        List<Long> delIds = new ArrayList<>();
        delIds.add(1L);
        delIds.add(2L);
        delIds.add(3L);
        userMapper.deleteBatchIds(delIds);
    }

    /**
     * 更新
     */
    @Test
    void update() {
        // 根据id更新
        User user = new User();
        user.setId(6L);
        user.setAge(23);
        userMapper.updateById(user);

        // 根据 whereWrapper 条件更新
        User user2 = new User();
        user2.setEmail("lxy@163.com");
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", "fgq");
        userMapper.update(user2, qw);
    }

    /**
     * 查询
     */
    @Test
    void select1() {
        // 根据 id 查询
        User user = userMapper.selectById(6);
        System.out.println("selectById：" + user);

        // 根据 id 批量查询
        List<User> list1 = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        list1.forEach(System.out::println);

        // 根据 entity 条件，查询一条记录，如果有多个结果会报错
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", "fgq");
        User user2 = userMapper.selectOne(qw);
        System.out.println("selectOne：" + user2);

        // 根据 entity 条件查询，返回的是 List<T>
        QueryWrapper<User> qw2 = new QueryWrapper<>();
        qw2.likeRight("email", "test");
        List<User> list2 = userMapper.selectList(qw2);
        list2.forEach(System.out::println);

        // 根据 entity 条件查询，返回的是 List<Map<String, Object>>
        List<Map<String, Object>> list3 = userMapper.selectMaps(qw2);
        list3.forEach(System.out::println);

        // 根据 entity 条件查询，注意：只返回第一个字段的值
        List<Object> list4 = userMapper.selectObjs(qw2);
        list4.forEach(System.out::println);

        // 根据 columnMap 条件查询
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("name", "fgq");
        List<User> list5 = userMapper.selectByMap(selectMap);
        list5.forEach(System.out::println);
    }


    /**
     * 查询
     */
    @Test
    void select2() {
        // 根据 Wrapper 条件，查询总记录数
        QueryWrapper<User> qw = new QueryWrapper<>();
        Integer count = userMapper.selectCount(qw);
        System.out.println(count);
        // 分页查询
        IPage<User> iPage = new Page<>(1, 3);
        userMapper.selectPage(iPage, null);
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页数量：" + iPage.getSize());
        System.out.println("总计页数：" + iPage.getPages());
        System.out.println("总计条数：" + iPage.getTotal());
        System.out.println("数据：" + iPage.getRecords());
    }


}
