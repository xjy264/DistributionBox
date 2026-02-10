package com.distributionbox.service.impl;

import com.distributionbox.entity.Box;
import com.distributionbox.entity.Components;
import com.distributionbox.entity.Inspection;
import com.distributionbox.mapper.BoxMapper;
import com.distributionbox.mapper.ComponentsMapper;
import com.distributionbox.mapper.InspectionMapper;
import com.distributionbox.service.IBoxService;
import com.distributionbox.service.IInspectionService;
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

