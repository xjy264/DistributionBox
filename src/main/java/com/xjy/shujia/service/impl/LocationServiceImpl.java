package com.xjy.shujia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjy.shujia.entity.Location;
import com.xjy.shujia.mapper.LocationMapper;
import com.xjy.shujia.service.ILocationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements ILocationService {

    @Override
    public List<Map<String, Object>> tree() {
        QueryWrapper<Location> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("level").orderByAsc("sort_num").orderByAsc("id");
        List<Location> all = list(wrapper);

        Map<Integer, List<Location>> groupByParent = all.stream()
                .collect(Collectors.groupingBy(item -> item.getParentId() == null ? 0 : item.getParentId()));

        List<Location> roots = all.stream()
                .filter(item -> item.getLevel() != null && item.getLevel() == 1)
                .collect(Collectors.toList());

        List<Map<String, Object>> result = new ArrayList<>();
        for (Location root : roots) {
            result.add(buildNode(root, groupByParent));
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> listView() {
        QueryWrapper<Location> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("level").orderByAsc("sort_num").orderByAsc("id");
        List<Location> all = list(wrapper);
        Map<Integer, Location> byId = all.stream()
                .collect(Collectors.toMap(Location::getId, item -> item));

        List<Map<String, Object>> result = new ArrayList<>();
        for (Location item : all) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", item.getId());
            row.put("parentId", item.getParentId());
            row.put("name", item.getName());
            row.put("level", item.getLevel());
            row.put("sortNum", item.getSortNum());

            String station = "";
            String area = "";
            String address = "";

            if (item.getLevel() != null) {
                if (item.getLevel() == 1) {
                    station = item.getName();
                } else if (item.getLevel() == 2) {
                    area = item.getName();
                    Location parent = byId.get(item.getParentId());
                    station = parent == null ? "" : parent.getName();
                } else if (item.getLevel() == 3) {
                    address = item.getName();
                    Location areaNode = byId.get(item.getParentId());
                    if (areaNode != null) {
                        area = areaNode.getName();
                        Location stationNode = byId.get(areaNode.getParentId());
                        station = stationNode == null ? "" : stationNode.getName();
                    }
                }
            }

            row.put("station", station);
            row.put("area", area);
            row.put("address", address);
            row.put("path", buildPath(station, area, address));
            result.add(row);
        }

        return result;
    }

    private Map<String, Object> buildNode(Location location, Map<Integer, List<Location>> groupByParent) {
        Map<String, Object> node = new HashMap<>();
        node.put("id", location.getId());
        node.put("value", location.getName());
        node.put("label", location.getName());
        node.put("name", location.getName());
        node.put("parentId", location.getParentId());
        node.put("level", location.getLevel());

        List<Location> children = groupByParent.getOrDefault(location.getId(), new ArrayList<>());
        if (!children.isEmpty()) {
            List<Map<String, Object>> childNodes = new ArrayList<>();
            for (Location child : children) {
                childNodes.add(buildNode(child, groupByParent));
            }
            node.put("children", childNodes);
        }

        return node;
    }

    private String buildPath(String station, String area, String address) {
        StringBuilder builder = new StringBuilder();
        if (!"".equals(station)) {
            builder.append(station);
        }
        if (!"".equals(area)) {
            if (builder.length() > 0) {
                builder.append(" / ");
            }
            builder.append(area);
        }
        if (!"".equals(address)) {
            if (builder.length() > 0) {
                builder.append(" / ");
            }
            builder.append(address);
        }
        return builder.toString();
    }
}
