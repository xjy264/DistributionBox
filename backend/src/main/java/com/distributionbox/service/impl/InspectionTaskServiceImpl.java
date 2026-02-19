package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.InspectionTask;
import com.distributionbox.mapper.InspectionTaskMapper;
import com.distributionbox.service.IInspectionTaskService;
import org.springframework.stereotype.Service;

@Service
public class InspectionTaskServiceImpl extends ServiceImpl<InspectionTaskMapper, InspectionTask> implements IInspectionTaskService {
}
