package com.distributionbox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.distributionbox.entity.BoxCircuit;
import java.util.List;

public interface IBoxCircuitService extends IService<BoxCircuit> {

    List<BoxCircuit> byBoxId(Integer boxId);

    int deleteByBoxId(Integer boxId);
}
