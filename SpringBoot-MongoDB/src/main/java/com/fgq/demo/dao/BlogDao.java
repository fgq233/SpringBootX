package com.fgq.demo.dao;


import com.fgq.demo.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogDao extends MongoRepository<Blog, String> {

    Page<Blog> findByUserid(String userid, Pageable pageable);

}