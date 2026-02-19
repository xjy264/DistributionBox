package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.EmergencyRepair;
import com.distributionbox.mapper.EmergencyRepairMapper;
import com.distributionbox.service.IEmergencyRepairService;
import org.springframework.stereotype.Service;

@Service
public class EmergencyRepairServiceImpl extends ServiceImpl<EmergencyRepairMapper, EmergencyRepair>
        implements IEmergencyRepairService {
}
