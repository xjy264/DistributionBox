package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.distributionbox.common.Result;
import com.distributionbox.entity.MaintenanceCompareImage;
import com.distributionbox.entity.MaintenanceDisease;
import com.distributionbox.entity.MaintenanceProcessImage;
import com.distributionbox.mapper.MaintenanceCompareImageMapper;
import com.distributionbox.mapper.MaintenanceDiseaseMapper;
import com.distributionbox.mapper.MaintenanceProcessImageMapper;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance-module")
public class MaintenanceModuleController {
    @Resource MaintenanceDiseaseMapper diseaseMapper;
    @Resource MaintenanceCompareImageMapper compareMapper;
    @Resource MaintenanceProcessImageMapper processMapper;

    @GetMapping("/disease")
    public Result diseaseList(@RequestParam Integer taskId){
        return Result.success(diseaseMapper.selectList(new QueryWrapper<MaintenanceDisease>().eq("task_id", taskId).orderByAsc("seq_no", "id")));
    }
    @PostMapping("/disease/save")
    public Result diseaseSave(@RequestBody MaintenanceDisease row){
        diseaseMapper.insert(row);
        return Result.success(true);
    }
    @PostMapping("/disease/update")
    public Result diseaseUpdate(@RequestBody MaintenanceDisease row){
        diseaseMapper.updateById(row);
        return Result.success(true);
    }
    @DeleteMapping("/disease/{id}")
    public Result diseaseDelete(@PathVariable Integer id){
        return Result.success(diseaseMapper.deleteById(id));
    }

    @GetMapping("/compare")
    public Result compareList(@RequestParam Integer taskId){
        List<MaintenanceCompareImage> rows = compareMapper.selectList(new QueryWrapper<MaintenanceCompareImage>().eq("task_id", taskId).orderByDesc("id"));
        return Result.success(rows);
    }
    @PostMapping("/compare/save")
    public Result compareSave(@RequestBody MaintenanceCompareImage row){
        if (row.getId() == null) compareMapper.insert(row); else compareMapper.updateById(row);
        return Result.success(true);
    }
    @DeleteMapping("/compare/{id}")
    public Result compareDelete(@PathVariable Integer id){
        return Result.success(compareMapper.deleteById(id));
    }

    @GetMapping("/process")
    public Result processList(@RequestParam Integer taskId){
        return Result.success(processMapper.selectList(new QueryWrapper<MaintenanceProcessImage>().eq("task_id", taskId).orderByDesc("id")));
    }
    @PostMapping("/process/save")
    public Result processSave(@RequestBody MaintenanceProcessImage row){
        if (row.getId() == null) processMapper.insert(row); else processMapper.updateById(row);
        return Result.success(true);
    }
    @DeleteMapping("/process/{id}")
    public Result processDelete(@PathVariable Integer id){
        return Result.success(processMapper.deleteById(id));
    }
}
