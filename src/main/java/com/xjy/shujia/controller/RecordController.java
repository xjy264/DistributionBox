package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Inspection;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IRecordService;
import com.xjy.shujia.entity.Record;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-07-16
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    private IRecordService recordService;

    @PostMapping
    public Result save(@RequestBody Record record) {
        return Result.success(recordService.saveOrUpdate(record)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(recordService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(recordService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(recordService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        return Result.success(recordService.page(new Page<>(pageNum, pageSize))) ;
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(recordService.removeBatchByIds(ids)) ;
    }

    @GetMapping("/getByPid/{pid}")
    public List<Record> getByPid(@PathVariable String pid) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        if (!"".equals(pid)) {
            queryWrapper.eq("pid", pid);
        }
        queryWrapper.orderByAsc("number");
        return recordService.list(queryWrapper);
    }
}
