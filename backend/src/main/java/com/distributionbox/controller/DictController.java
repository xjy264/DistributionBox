package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.entity.Dict;
import com.distributionbox.mapper.DictMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private DictMapper dictMapper;

    @GetMapping
    public List<Dict> list() {
        return dictMapper.selectList(new QueryWrapper<Dict>().orderByAsc("sort"));
    }

    @GetMapping("/type/{type}")
    public List<Dict> listByType(@PathVariable String type) {
        return dictMapper.selectList(
            new QueryWrapper<Dict>()
                .eq("type", type)
                .orderByAsc("sort")
        );
    }

    @GetMapping("/page")
    public IPage<Dict> page(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String type,
                            @RequestParam(defaultValue = "") String name) {
        IPage<Dict> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if (!"".equals(type)) {
            queryWrapper.eq("type", type);
        }
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByAsc("type", "sort");
        return dictMapper.selectPage(page, queryWrapper);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Dict dict) {
        if (dict.getId() == null) {
            return dictMapper.insert(dict) > 0;
        }
        return dictMapper.updateById(dict) > 0;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return dictMapper.deleteById(id) > 0;
    }
}
