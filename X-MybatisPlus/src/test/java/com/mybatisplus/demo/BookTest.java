package com.mybatisplus.demo;

import com.mybatisplus.demo.domain.Book;
import com.mybatisplus.demo.domain.User;
import com.mybatisplus.demo.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookTest {

    @Autowired
    private BookMapper bookMapper;


    /**
     * 一对一查询
     */
    @Test
    void query3() {
//        XML版
        List<Book> list = bookMapper.find1To1();
//        注解版
//        List<Book> list = bookMapper.find1To1X();
        list.forEach(System.out::println);
    }


    /**
     * 一对多查询
     */
    @Test
    void query4() {
//        XML版
        List<User> list = bookMapper.find1ToMore(6);
//        注解版
//        List<User> list = bookMapper.find1ToMoreX(6);
        list.forEach(System.out::println);
    }

}
