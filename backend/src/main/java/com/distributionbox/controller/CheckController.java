package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.entity.Inspection;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.distributionbox.common.Result;

import com.distributionbox.service.ICheckService;
import com.distributionbox.entity.Check;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-07-15
 */
@RestController
@RequestMapping("/check")
public class CheckController {
    @Resource
    private ICheckService checkService;

    @PostMapping
    public Result save(@RequestBody Check check) {
        return Result.success(checkService.saveOrUpdate(check)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(checkService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(checkService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(checkService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        return Result.success(checkService.page(new Page<>(pageNum, pageSize))) ;
    }

    @GetMapping("/getByPid/{pid}")
    public List<Check> getByPid(@PathVariable String pid) {
        QueryWrapper<Check> queryWrapper = new QueryWrapper<>();
        if (!"".equals(pid)) {
            queryWrapper.eq("pid", pid);
        }
        return checkService.list(queryWrapper);
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(checkService.removeBatchByIds(ids)) ;
    }

    @PostMapping("/addCheck/{pid}")
    public boolean addCheck(@PathVariable String pid){
//        Check check = new Check();
//        check.setPid(pid);
//        check.setContent("编号标识");

        ArrayList<Check> checks = new ArrayList<>();

        String[] content = {"编号标识","防触电标识","箱体外观","箱体稳固性","箱体门锁","电缆标牌",
                "系统图","元器件标签","箱内配线","元器件是否松动","元器件工作温度","避雷保护装置",
                "箱门跨接线","进出线密封","零排接线","地排接线","隔弧挡片","清灰保洁","紧固螺丝",
                "其他","电线路工作状态","电线路是否老化开裂","导线及连接处工作温度","出线处绝缘是否完好",
                "出线处放电变色情况","电线路相序","桥架是否完好","桥架连接处接地跨接","桥架盖是否完好","其他"};

        for (int i=0;i<content.length;i++){
            Check check = new Check();
            check.setPid(pid);
            check.setContent(content[i]);
            checks.add(check);
        }

        return checkService.saveBatch(checks);
    }
}
