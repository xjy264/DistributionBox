package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Inspection;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IDetectService;
import com.xjy.shujia.entity.Detect;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-07-14
 */
@RestController
@RequestMapping("/detect")
public class DetectController {

    @Resource
    private IDetectService detectService;

    @PostMapping
    public Result save(@RequestBody Detect detect) {
        return Result.success(detectService.saveOrUpdate(detect)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(detectService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(detectService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(detectService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        return Result.success(detectService.page(new Page<>(pageNum, pageSize))) ;
    }

    @GetMapping("/getByPid/{pid}")
    public Detect getByPid(@PathVariable Integer pid) {
        QueryWrapper<Detect> queryWrapper = new QueryWrapper<>();
        if (!"".equals(pid)) {
            queryWrapper.eq("pid", pid);
        }
        return detectService.getOne(queryWrapper);
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(detectService.removeBatchByIds(ids)) ;
    }
}
