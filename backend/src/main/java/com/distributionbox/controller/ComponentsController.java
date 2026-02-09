package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.mapper.ComponentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.distributionbox.common.Result;

import com.distributionbox.service.IComponentsService;
import com.distributionbox.entity.Components;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-04-09
 */
@RestController
@RequestMapping("/components")
public class ComponentsController {

    @Resource
    private IComponentsService componentsService;

    @Autowired
    private ComponentsMapper componentsMapper;


    //查找全部
    @GetMapping("/getAll")
    public List<Components> index() {
        return componentsService.list();
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Components components){
        return componentsService.saveOrUpdate(components);
    }

    @GetMapping("/{id}")
    public List<Components> selectById(@PathVariable Integer id) {
        List<Components> components = componentsService.byBoxId(id);
        return components;
    }

    @DeleteMapping("/{id}")
    public  Integer delete(@PathVariable Integer id){
        return componentsMapper.deleteById(id);
    }

    @GetMapping("/page")
    public IPage<Components> findPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(defaultValue = "") String box_id){
        IPage<Components> boxPage = new Page<>(pageNum,pageSize);
        QueryWrapper<Components> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("box_id",box_id);
        IPage<Components> page = componentsService.page(boxPage, queryWrapper);
        return page;
    }
}
