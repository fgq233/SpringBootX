package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatisplus.demo.domain.Book;
import com.mybatisplus.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface BookMapper extends BaseMapper<Book> {

    List<Book> findByIf(Book order);

    List<Book> findByList(List<Integer> list);

    List<Book> findByList2(List<Integer> list);

    List<Book> findByUserId(Integer userId);

    List<Book> find1To1();

    List<User> find1ToMore(Integer id);

    /**
     * 一对一(注解版)
     */
    @Select("SELECT * FROM sys_book")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "is_deleted", property = "isDeleted"),
            //  column = "user_id" 是关联的键
            @Result(column = "user_id", property = "user", javaType = User.class,
                    one = @One(select = "com.mybatisplus.demo.mapper.UserMapper.selectById", fetchType = FetchType.EAGER)),
    })
    List<Book> find1To1X();

    /**
     * 一对多(注解版)
     */
    @Select("SELECT * FROM sys_user where id = #{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "age", property = "age"),
            @Result(column = "email", property = "email"),
            @Result(column = "is_deleted", property = "isDeleted"),
            //  column = "id" 是关联的键
            @Result(column = "id", property = "bookList", javaType = List.class,
                    many = @Many(select = "com.mybatisplus.demo.mapper.BookMapper.findByUserId", fetchType = FetchType.EAGER)),
    })
    List<User> find1ToMoreX(Integer userId);
}
