package com.elasticsearch.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elasticsearch.demo.dao.HotelMapper;
import com.elasticsearch.demo.dao.HotelRepository;
import com.elasticsearch.demo.domain.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 通过 ElasticsearchRepository 实现增删改查
 */
@Service
public class CurdService implements ICurdService {

    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public void save(Hotel hotel) {
        // 保存到数据库
        if (hotel.getId() == null) {
            hotelMapper.insert(hotel);
        }
        // 保存到索引库
        hotelRepository.save(hotel);
    }

    @Override
    public void saveAll() {
        List<Hotel> hotels = hotelMapper.selectList(new QueryWrapper<>());
        hotelRepository.saveAll(hotels);
    }

    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        hotelRepository.deleteAll();
    }

    @Override
    public void delSelection(List<Hotel> hotelList) {
        hotelRepository.deleteAll(hotelList);
    }

    @Override
    public Hotel findById(Long id) {
        Optional<Hotel> optional = hotelRepository.findById(id);
        return optional.get();
    }

    @Override
    public Page<Hotel> getPageList(Integer curPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(curPage - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Page<Hotel> findByName(String searchVal, Integer curPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(curPage - 1, pageSize);
        return hotelRepository.findByName(searchVal, pageable);
    }


}
