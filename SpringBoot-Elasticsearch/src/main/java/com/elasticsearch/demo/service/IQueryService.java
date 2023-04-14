package com.elasticsearch.demo.service;

import com.elasticsearch.demo.domain.Hotel;
import org.springframework.data.domain.Page;

public interface IQueryService {

    /**
     * 组合查询
     * @param searchVal  参与分词的搜索条件
     * @param brand 品牌(精确匹配)
     * @param city 城市(精确匹配)
     * @param starName 星级(精确匹配)
     * @param sort 排序条件
     * @param highlight 是否高亮搜索条件
     * @param curPage   当前页
     * @param pageSize  每页数量
     */
    Page<Hotel> searchPageList(String searchVal, String brand, String city, String starName, Integer sort, Integer highlight, Integer curPage, Integer pageSize);



}
