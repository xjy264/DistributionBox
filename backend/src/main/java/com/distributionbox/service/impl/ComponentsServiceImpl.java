package com.distributionbox.service.impl;

import com.distributionbox.entity.Components;
import com.distributionbox.mapper.ComponentsMapper;
import com.distributionbox.service.IComponentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2024-04-09
 */
@Service
public class ComponentsServiceImpl extends ServiceImpl<ComponentsMapper, Components> implements IComponentsService {

    @Autowired
    private ComponentsMapper componentsMapper;

    @Override
    public List<Components> byBoxId(Integer id) {
        return componentsMapper.byBoxId(id);
    }
}
