package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.RepairTaskSaveDto;
import com.distributionbox.controller.dto.RepairTaskViewDto;
import com.distributionbox.entity.Box;
import com.distributionbox.entity.RepairTask;
import com.distributionbox.service.IBoxService;
import com.distributionbox.service.IRepairTaskService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/overhaul-task", "/repair-task"})
public class RepairTaskController {

    @Resource
    private IRepairTaskService repairTaskService;

    @Resource
    private IBoxService boxService;

    @PostMapping("/save")
    public Result save(@RequestBody RepairTaskSaveDto dto) {
        RepairTask task = new RepairTask();
        BeanUtils.copyProperties(dto, task);
        if (task.getTaskNo() == null || task.getTaskNo().isBlank()) {
            task.setTaskNo("RT" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        }
        return Result.success(repairTaskService.saveOrUpdate(task));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(repairTaskService.removeById(id));
    }

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String taskNo,
                       @RequestParam(defaultValue = "") String reportUser,
                       @RequestParam(defaultValue = "") String rescueUsers,
                       @RequestParam(required = false) Integer boxId,
                       @RequestParam(defaultValue = "") String reportTimeStart,
                       @RequestParam(defaultValue = "") String reportTimeEnd) {
        QueryWrapper<RepairTask> queryWrapper = new QueryWrapper<>();
        if (!taskNo.isBlank()) {
            queryWrapper.like("task_no", taskNo.trim());
        }
        if (!reportUser.isBlank()) {
            queryWrapper.like("report_user", reportUser.trim());
        }
        if (!rescueUsers.isBlank()) {
            queryWrapper.like("rescue_users", rescueUsers.trim());
        }
        if (boxId != null) {
            queryWrapper.eq("box_id", boxId);
        }
        if (!reportTimeStart.isBlank()) {
            queryWrapper.ge("report_time", reportTimeStart.trim());
        }
        if (!reportTimeEnd.isBlank()) {
            queryWrapper.le("report_time", reportTimeEnd.trim());
        }
        queryWrapper.orderByDesc("id");

        IPage<RepairTask> page = repairTaskService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<RepairTaskViewDto> records = page.getRecords().stream().map(task -> {
            RepairTaskViewDto view = new RepairTaskViewDto();
            BeanUtils.copyProperties(task, view);
            if (task.getBoxId() != null) {
                Box box = boxService.getById(task.getBoxId());
                if (box != null) {
                    view.setBoxAccount(box.getBoxId());
                }
            }
            return view;
        }).toList();

        return Result.success(Map.of(
                "records", records,
                "total", page.getTotal(),
                "current", page.getCurrent(),
                "size", page.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        RepairTask task = repairTaskService.getById(id);
        if (task == null) {
            return Result.success(null);
        }
        RepairTaskViewDto view = new RepairTaskViewDto();
        BeanUtils.copyProperties(task, view);
        if (task.getBoxId() != null) {
            Box box = boxService.getById(task.getBoxId());
            if (box != null) {
                view.setBoxAccount(box.getBoxId());
            }
        }
        return Result.success(view);
    }
}
