package com.distributionbox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.distributionbox.entity.Location;

import java.util.List;
import java.util.Map;

public interface ILocationService extends IService<Location> {
    List<Map<String, Object>> tree();

    List<Map<String, Object>> listView();
}
