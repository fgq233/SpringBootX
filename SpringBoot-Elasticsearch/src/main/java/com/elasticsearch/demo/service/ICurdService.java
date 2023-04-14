package com.elasticsearch.demo.service;

import com.elasticsearch.demo.domain.Hotel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICurdService {

    /**
     * 单个新增
     */
    void save(Hotel hotel);

    /**
     * 批量保存
     */
    void saveAll();

    /**
     * 单个删除
     */
    void deleteById(Long id);

    /**
     * 批量删除 or 清空索引库
     */
    void deleteAll();

    /**
     * 批量删除
     */
    void delSelection(List<Hotel> hotelList);

    /**
     * 根据id查询索引库中的文档
     */
    Hotel findById(Long id);

    /**
     * 分页查询
     */
    Page<Hotel> getPageList(Integer curPage, Integer pageSize);

    /**
     * 根据名称分词查询
     */
    Page<Hotel> findByName(String name, Integer curPage, Integer pageSize);

}
