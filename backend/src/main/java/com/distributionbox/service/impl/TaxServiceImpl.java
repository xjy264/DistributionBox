package com.distributionbox.service.impl;

import com.distributionbox.entity.Tax;
import com.distributionbox.mapper.TaxMapper;
import com.distributionbox.service.ITaxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-12-17
 */
@Service
public class TaxServiceImpl extends ServiceImpl<TaxMapper, Tax> implements ITaxService {

}
