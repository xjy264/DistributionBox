package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.ITaxService;
import com.xjy.shujia.entity.Tax;

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
@RequestMapping("/tax")
public class TaxController {

    @Resource
    private ITaxService taxService;

    @PostMapping
    public Result save(@RequestBody Tax tax) {
        return Result.success(taxService.saveOrUpdate(tax)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(taxService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(taxService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(taxService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        return Result.success(taxService.page(new Page<>(pageNum, pageSize))) ;
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(taxService.removeBatchByIds(ids)) ;
    }
}
