package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.BoxCircuit;
import com.distributionbox.mapper.BoxCircuitMapper;
import com.distributionbox.service.IBoxCircuitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoxCircuitServiceImpl extends ServiceImpl<BoxCircuitMapper, BoxCircuit> implements IBoxCircuitService {

    @Autowired
    private BoxCircuitMapper boxCircuitMapper;

    @Override
    public List<BoxCircuit> byBoxId(Integer boxId) {
        return boxCircuitMapper.byBoxId(boxId);
    }

    @Override
    public int deleteByBoxId(Integer boxId) {
        return boxCircuitMapper.deleteByBoxId(boxId);
    }
}
