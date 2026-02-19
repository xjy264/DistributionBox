package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Box;
import com.xjy.shujia.entity.Components;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IInspectionService;
import com.xjy.shujia.entity.Inspection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/inspection")
public class InspectionController {

    @Resource
    private IInspectionService inspectionService;

    //查找全部
    @GetMapping
    public List<Inspection> index(){
        return inspectionService.list();
    }

    @GetMapping("/getByPid/{pid}")
    public Inspection getByPid(@PathVariable String pid) {
        QueryWrapper<Inspection> queryWrapper = new QueryWrapper<>();
        if (!"".equals(pid)) {
            queryWrapper.eq("pid", pid);
        }
        return inspectionService.getOne(queryWrapper);
    }

    @GetMapping("/getList/{id}")
    public List<Inspection> getList(@PathVariable String id) {
        List<Inspection> inspection = inspectionService.byInspectionId(id);
        return inspection;
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Inspection inspection){
        return inspectionService.saveOrUpdate(inspection);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return inspectionService.removeById(id);
    }

    @PostMapping("/saveByPid/{pid}")
    public boolean saveByPid(@PathVariable String pid){
        Inspection inspection = new Inspection();
        String nullUrl = "0d0bb679724743399745578319259715.jpg";
        inspection.setPid(pid);
        inspection.setAppearanceUrl(nullUrl);
        inspection.setFirstUrl(nullUrl);
        inspection.setSecondUrl(nullUrl);
        inspection.setThirdUrl(nullUrl);
        inspection.setFifthUrl(nullUrl);
        inspection.setFourthUrl(nullUrl);
        inspection.setEarlierUrl(nullUrl);
        inspection.setLaterUrl(nullUrl);
        return inspectionService.saveOrUpdate(inspection);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        return Result.success(inspectionService.page(new Page<>(pageNum, pageSize))) ;
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(inspectionService.removeBatchByIds(ids)) ;
    }
}
