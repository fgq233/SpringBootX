package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.domain.Hotel;
import com.elasticsearch.demo.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 查询 Controller
 */
@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private IQueryService templateService;


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
    @RequestMapping("/singleConditionSearch")
    @ResponseBody
    public Page<Hotel> singleConditionSearch(String condition, String val, Integer valStart, Integer valEnd, String kssj, String jssj, Integer curPage, Integer pageSize) {
        return templateService.singleConditionSearch(condition, val, valStart, valEnd, kssj, jssj, curPage, pageSize);
    }


    /**
     * 组合查询
     *
     * @param searchVal 参与分词的搜索条件
     * @param brand     品牌(精确匹配)
     * @param city      城市(精确匹配)
     * @param starName  星级(精确匹配)
     * @param sort      排序条件
     * @param highlight 是否高亮搜索条件
     * @param curPage   当前页
     * @param pageSize  每页数量
     */
    @RequestMapping("/searchPageList")
    @ResponseBody
    public Page<Hotel> searchPageList(String searchVal, String brand, String city, String starName, Integer sort, String kssj, String jssj,
                                      Boolean highlight, Boolean functionScore, Integer curPage, Integer pageSize) {
        return templateService.searchPageList(searchVal, brand, city, starName, kssj, jssj, sort, highlight, functionScore, curPage, pageSize);
    }

}
