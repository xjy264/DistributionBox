package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjy.shujia.common.Constants;
import com.xjy.shujia.entity.Location;
import com.xjy.shujia.exception.ServiceException;
import com.xjy.shujia.service.ILocationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Resource
    private ILocationService locationService;

    @GetMapping("/tree")
    public List<Map<String, Object>> tree() {
        return locationService.tree();
    }

    @GetMapping("/list")
    public List<Map<String, Object>> list() {
        return locationService.listView();
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Location location) {
        validateLocation(location);
        return locationService.saveOrUpdate(location);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        long children = locationService.count(new QueryWrapper<Location>().eq("parent_id", id));
        if (children > 0) {
            throw new ServiceException(Constants.CODE_400, "请先删除下级节点");
        }
        return locationService.removeById(id);
    }

    private void validateLocation(Location location) {
        if (location.getLevel() == null || location.getLevel() < 1 || location.getLevel() > 3) {
            throw new ServiceException(Constants.CODE_400, "层级必须为 1-3");
        }
        if (location.getName() == null || location.getName().trim().isEmpty()) {
            throw new ServiceException(Constants.CODE_400, "名称不能为空");
        }
        location.setName(location.getName().trim());

        Integer parentId = location.getParentId() == null ? 0 : location.getParentId();
        if (location.getLevel() == 1) {
            location.setParentId(0);
            return;
        }

        if (parentId <= 0) {
            throw new ServiceException(Constants.CODE_400, "请选择上级节点");
        }

        Location parent = locationService.getById(parentId);
        if (parent == null) {
            throw new ServiceException(Constants.CODE_400, "上级节点不存在");
        }

        if (location.getLevel() == 2 && parent.getLevel() != 1) {
            throw new ServiceException(Constants.CODE_400, "工区的上级必须是车间");
        }

        if (location.getLevel() == 3 && parent.getLevel() != 2) {
            throw new ServiceException(Constants.CODE_400, "安装地点的上级必须是工区");
        }
    }
}
