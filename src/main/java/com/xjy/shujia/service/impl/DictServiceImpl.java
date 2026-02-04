package com.xjy.shujia.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjy.shujia.common.Constants;
import com.xjy.shujia.controller.dto.UserDto;
import com.xjy.shujia.entity.Dict;
import com.xjy.shujia.entity.User;
import com.xjy.shujia.exception.ServiceException;
import com.xjy.shujia.mapper.DictMapper;
import com.xjy.shujia.mapper.UserMapper;
import com.xjy.shujia.service.IDictService;
import com.xjy.shujia.service.IUserService;
import com.xjy.shujia.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-07-14
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
}
