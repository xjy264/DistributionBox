package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.InspectionTaskSaveDto;
import com.distributionbox.entity.InspectionTask;
import com.distributionbox.service.IInspectionTaskService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/maintenance-task", "/inspection-task"})
public class InspectionTaskController {

    @Resource
    private IInspectionTaskService inspectionTaskService;

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody InspectionTaskSaveDto dto) {
        InspectionTask task = new InspectionTask();
        BeanUtils.copyProperties(dto, task);
        if (task.getInspectionTime() == null) {
            task.setInspectionTime(LocalDateTime.now());
        }
        if (task.getTaskNo() == null || task.getTaskNo().isBlank()) {
            return Result.error("400", "taskNo不能为空（手动填写）");
        }
        inspectionTaskService.saveOrUpdate(task);
        return Result.success(task.getId());
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable Integer id) {
        return Result.success(inspectionTaskService.removeById(id));
    }

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String inspectionUser,
                       @RequestParam(defaultValue = "") String taskNo,
                       @RequestParam(defaultValue = "") String inspectionTime,
                       @RequestParam(defaultValue = "") String inspectionTimeStart,
                       @RequestParam(defaultValue = "") String inspectionTimeEnd,
                       @RequestParam(defaultValue = "") String maintenanceType) {
        QueryWrapper<InspectionTask> queryWrapper = new QueryWrapper<>();
        if (!inspectionUser.isBlank()) queryWrapper.like("inspection_user", inspectionUser.trim());
        if (!taskNo.isBlank()) queryWrapper.like("task_no", taskNo.trim());
        if (!inspectionTime.isBlank()) queryWrapper.like("inspection_time", inspectionTime.trim());
        if (!inspectionTimeStart.isBlank()) queryWrapper.ge("inspection_time", inspectionTimeStart.trim());
        if (!inspectionTimeEnd.isBlank()) queryWrapper.le("inspection_time", inspectionTimeEnd.trim());
        if (!maintenanceType.isBlank()) queryWrapper.eq("maintenance_type", maintenanceType.trim());
        queryWrapper.orderByDesc("id");
        IPage<InspectionTask> page = inspectionTaskService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(Map.of(
                "records", page.getRecords(),
                "total", page.getTotal(),
                "current", page.getCurrent(),
                "size", page.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(inspectionTaskService.getById(id));
    }
}
