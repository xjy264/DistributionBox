package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Home;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IMaintenanceService;
import com.xjy.shujia.entity.Maintenance;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Resource
    private IMaintenanceService maintenanceService;

    @PostMapping("/save")
    public Result save(@RequestBody Maintenance maintenance) {
        return Result.success(maintenanceService.saveOrUpdate(maintenance)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(maintenanceService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(maintenanceService.list()) ;
    }

    @GetMapping("/getById/{id}")
    public Maintenance findOne(@PathVariable Integer id) {
        return maintenanceService.getById(id) ;
    }

    @GetMapping("/page")
    public IPage<Maintenance> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String faultPhenomenon,
                                @RequestParam(defaultValue = "") String pid){
        IPage<Maintenance> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Maintenance> queryWrapper = new QueryWrapper<>();
        if (!"".equals(faultPhenomenon)) {
            queryWrapper.like("faultPhenomenon", faultPhenomenon);
        }
        queryWrapper.eq("pid",pid);
        queryWrapper.orderByDesc("id");
        return maintenanceService.page(page, queryWrapper);
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(maintenanceService.removeBatchByIds(ids)) ;
    }
}
