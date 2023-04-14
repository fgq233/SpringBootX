package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.domain.Hotel;
import com.elasticsearch.demo.domain.JsonResult;
import com.elasticsearch.demo.service.ICurdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 基本的增删改查 Controller
 */
@Controller
@RequestMapping("/curd")
public class CurdController {

    @Autowired
    private ICurdService curdService;


    /**
     * 单个新增
     */
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult saveHotel(Hotel hotel) {
        curdService.save(hotel);
        return JsonResult.ok();
    }

    /**
     * 批量新增
     */
    @RequestMapping("/saveAll")
    @ResponseBody
    public JsonResult saveAll() {
        curdService.saveAll();
        return JsonResult.ok();
    }

    /**
     * 单个删除
     */
    @RequestMapping("/deleteById")
    @ResponseBody
    public JsonResult deleteById(Long id) {
        curdService.deleteById(id);
        return JsonResult.ok();
    }

    /**
     * 清空索引库
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    public JsonResult deleteAll() {
        curdService.deleteAll();
        return JsonResult.ok();
    }

    /**
     * 批量删除
     */
    @RequestMapping("/delSelection")
    @ResponseBody
    public JsonResult delSelection(@RequestBody List<Hotel> hotelList) {
        curdService.delSelection(hotelList);
        return JsonResult.ok();
    }

    /**
     * 单个查询
     */
    @RequestMapping("/findById")
    @ResponseBody
    public JsonResult findById(Long id) {
        return JsonResult.ok().setData(curdService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param curPage  当前页
     * @param pageSize 每页数量
     */
    @RequestMapping("/getPageList")
    @ResponseBody
    public Page<Hotel> getPageList(Integer curPage, Integer pageSize) {
        return curdService.getPageList(curPage, pageSize);
    }

    /**
     * 根据名称分词查询
     *
     * @param searchVal  名称
     * @param curPage  当前页
     * @param pageSize 每页数量
     */
    @RequestMapping("/search")
    @ResponseBody
    public Page<Hotel> findByName(String searchVal, Integer curPage, Integer pageSize) {
        return curdService.findByName(searchVal, curPage, pageSize);
    }
}
