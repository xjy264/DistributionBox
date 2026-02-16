package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.RepairTask;
import com.distributionbox.mapper.RepairTaskMapper;
import com.distributionbox.service.IRepairTaskService;
import org.springframework.stereotype.Service;

@Service
public class RepairTaskServiceImpl extends ServiceImpl<RepairTaskMapper, RepairTask> implements IRepairTaskService {
}
