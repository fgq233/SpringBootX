package com.fgq.demo;

import com.fgq.demo.dao.BlogDao;
import com.fgq.demo.domain.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

@SpringBootTest
class MongoDBTest {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存
     */
    @Test
    void save() {
        Blog blog = new Blog();
//      ID 如果省略，MongoDB会自动生成主键
//        blog.setId("1");
        blog.setUserid("1001");
        blog.setContent("范老师帅的一批");
        blog.setCreatetime(new Date());
        blog.setStarnum(666);
        blogDao.save(blog);
    }

    /**
     * 根据id查询
     */
    @Test
    void findById() {
        Blog blog = blogDao.findById("1").get();
        System.out.println(blog);
    }

    /**
     * 根据id删除
     */
    @Test
    void deleteById() {
        blogDao.deleteById("1");
    }

    /**
     * 更新
     */
    @Test
    void update() {
        Blog blog = new Blog();
        blog.setId("1");
        blog.setUserid("1003");
        blog.setContent("女神下面香不香");
        blog.setCreatetime(new Date());
        blog.setStarnum(888);
        blogDao.save(blog);
    }


    /**
     * 查询所有
     */
    @Test
    void findAll() {
        List<Blog> list = blogDao.findAll();
        for (Blog blog : list) {
            System.out.println(blog);
        }
    }


    /**
     * 分页查询
     */
    @Test
    void findPage() {
        Page<Blog> page = blogDao.findByUserid("1001", PageRequest.of(1, 1));
        System.out.println("总数：" + page.getTotalElements());
        List<Blog> list = page.getContent();
        for (Blog blog : list) {
            System.out.println(blog);
        }
    }

    /**
     * 测试 MongoTemplate
     */
    @Test
    void testMongoTemplate() {
        //  查询条件
        Query query = Query.query(Criteria.where("userid").is("1001"));
        //  更新条件
        Update update = new Update();
        update.inc("starnum");
        mongoTemplate.updateFirst(query, update, Blog.class);
    }

}
