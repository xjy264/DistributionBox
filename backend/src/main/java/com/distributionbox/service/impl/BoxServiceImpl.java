package com.distributionbox.service.impl;

import com.distributionbox.entity.Box;
import com.distributionbox.mapper.BoxMapper;
import com.distributionbox.service.IBoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2024-04-09
 */
@Service
public class BoxServiceImpl extends ServiceImpl<BoxMapper, Box> implements IBoxService {

}
