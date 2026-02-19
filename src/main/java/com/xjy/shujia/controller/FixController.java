package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Components;
import com.xjy.shujia.entity.Home;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IFixService;
import com.xjy.shujia.entity.Fix;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-05-17
 */
@RestController
@RequestMapping("/fix")
public class FixController {

    @Resource
    private IFixService fixService;

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(fixService.removeById(id)) ;
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Fix fix){
        return fixService.saveOrUpdate(fix);
    }

    @GetMapping
    public Result findAll() {
        return Result.success(fixService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(fixService.list()) ;
    }

    @GetMapping("/page")
    public IPage<Fix> findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String boxId,
                           @RequestParam(defaultValue = "") String code,
                           @RequestParam(defaultValue = "") String user){
        IPage<Fix> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Fix> queryWrapper = new QueryWrapper<>();
        if (!"".equals(boxId)) {
            queryWrapper.like("box_id", boxId);
        }
        if (!"".equals(code)) {
            queryWrapper.like("code", code);
        }
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        queryWrapper.orderByDesc("time");
        return fixService.page(page, queryWrapper);
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(fixService.removeBatchByIds(ids)) ;
    }
}
