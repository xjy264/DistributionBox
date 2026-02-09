package com.distributionbox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.distributionbox.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
