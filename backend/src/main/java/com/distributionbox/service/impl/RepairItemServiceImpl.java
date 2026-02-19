package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.RepairItem;
import com.distributionbox.mapper.RepairItemMapper;
import com.distributionbox.service.IRepairItemService;
import org.springframework.stereotype.Service;

@Service
public class RepairItemServiceImpl extends ServiceImpl<RepairItemMapper, RepairItem> implements IRepairItemService {
}
