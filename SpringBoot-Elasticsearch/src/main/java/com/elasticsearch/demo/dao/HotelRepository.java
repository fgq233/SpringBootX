package com.elasticsearch.demo.dao;

import com.elasticsearch.demo.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface HotelRepository extends ElasticsearchRepository<Hotel, Long> {

    /**
     * 根据名称进行检索
     */
    Page<Hotel> findByName(String name, Pageable page);

}
