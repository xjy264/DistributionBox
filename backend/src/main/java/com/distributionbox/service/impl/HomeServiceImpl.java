package com.distributionbox.service.impl;

import com.distributionbox.entity.Home;
import com.distributionbox.mapper.HomeMapper;
import com.distributionbox.service.IHomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2024-04-08
 */
@Service
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Home> implements IHomeService {

}
