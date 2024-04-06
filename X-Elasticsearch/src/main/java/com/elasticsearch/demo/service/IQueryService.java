package com.elasticsearch.demo.service;

import com.elasticsearch.demo.domain.Hotel;
import org.springframework.data.domain.Page;

public interface IQueryService {

    /**
     * 单条件查询
     *
     * @param condition 查询条件
     * @param val       查询条件的值
     * @param valStart  查询条件的值(开始)
     * @param valEnd    查询条件的值(结束)
     * @param kssj      开始时间
     * @param jssj      结束时间
     * @param curPage   当前页
     * @param pageSize  每页数量
     */
    Page<Hotel> singleConditionSearch(String condition, String val, Integer valStart, Integer valEnd, String kssj, String jssj, Integer curPage, Integer pageSize);

    /**
     * 组合查询
     * @param searchVal  参与分词的搜索条件
     * @param brand 品牌(精确匹配)
     * @param city 城市(精确匹配)
     * @param starName 星级(精确匹配)
     * @param kssj 开始时间
     * @param jssj 结束时间
     * @param sort 排序条件
     * @param highlight 是否高亮搜索条件
     * @param functionScore 是否开启算分函数
     * @param curPage   当前页
     * @param pageSize  每页数量
     */
    Page<Hotel> searchPageList(String searchVal, String brand, String city, String starName, String kssj, String jssj, Integer sort,
                               Boolean highlight, Boolean functionScore, Integer curPage, Integer pageSize);



}
