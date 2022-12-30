package com.fgq.demo.mapper;

import com.fgq.demo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {

    User findById(Long id);

}
