package com.xjy.shujia.service;

import com.xjy.shujia.controller.dto.UserDto;
import com.xjy.shujia.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjy
 * @since 2022-07-14
 */
public interface IUserService extends IService<User> {

    UserDto login(UserDto userDto);

    User register(User user);
}
