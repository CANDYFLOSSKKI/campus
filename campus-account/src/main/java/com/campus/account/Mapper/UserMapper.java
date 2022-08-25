package com.campus.account.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.account.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {}
