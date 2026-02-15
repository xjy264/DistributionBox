package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.InspectionItem;
import com.distributionbox.mapper.InspectionItemMapper;
import com.distributionbox.service.IInspectionItemService;
import org.springframework.stereotype.Service;

@Service
public class InspectionItemServiceImpl extends ServiceImpl<InspectionItemMapper, InspectionItem> implements IInspectionItemService {
}
