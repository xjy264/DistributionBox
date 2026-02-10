package com.distributionbox.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.distributionbox.common.Result;

import com.distributionbox.service.IDepartmentService;
import com.distributionbox.entity.Department;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2022-12-17
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private IDepartmentService departmentService;

    @PostMapping
    public Result save(@RequestBody Department department) {
        return Result.success(departmentService.saveOrUpdate(department)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(departmentService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(departmentService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(departmentService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        return Result.success(departmentService.page(new Page<>(pageNum, pageSize))) ;
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(departmentService.removeBatchByIds(ids)) ;
    }
}
