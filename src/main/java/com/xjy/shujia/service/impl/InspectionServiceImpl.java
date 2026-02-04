package com.xjy.shujia.service.impl;

import com.xjy.shujia.entity.Box;
import com.xjy.shujia.entity.Components;
import com.xjy.shujia.entity.Inspection;
import com.xjy.shujia.mapper.BoxMapper;
import com.xjy.shujia.mapper.ComponentsMapper;
import com.xjy.shujia.mapper.InspectionMapper;
import com.xjy.shujia.service.IBoxService;
import com.xjy.shujia.service.IInspectionService;
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
 * @since 2024-07-06
 */
@Service
public class InspectionServiceImpl extends ServiceImpl<InspectionMapper, Inspection> implements IInspectionService {

    @Autowired
    private InspectionMapper inspectionMapper;

    @Override
    public List<Inspection> byInspectionId(String id) {
        return inspectionMapper.byInspectionId(id);
    }
}

